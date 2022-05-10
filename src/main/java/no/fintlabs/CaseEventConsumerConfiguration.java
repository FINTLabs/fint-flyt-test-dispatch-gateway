package no.fintlabs;

import no.fint.model.resource.arkiv.noark.SakResource;
import no.fintlabs.flyt.kafka.requestreply.InstanceFlowReplyProducerRecord;
import no.fintlabs.flyt.kafka.requestreply.InstanceFlowRequestConsumerFactoryService;
import no.fintlabs.kafka.common.topic.TopicCleanupPolicyParameters;
import no.fintlabs.kafka.requestreply.topic.RequestTopicNameParameters;
import no.fintlabs.kafka.requestreply.topic.RequestTopicService;
import no.fintlabs.model.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

@Configuration
public class CaseEventConsumerConfiguration {

    @Bean
    public ConcurrentMessageListenerContainer<String, SakResource> incomingInstanceConsumer(
            RequestTopicService requestTopicService,
            InstanceFlowRequestConsumerFactoryService instanceFlowRequestConsumerFactoryService,
            StatusService statusService
    ) {
        RequestTopicNameParameters requestTopicNameParameters = RequestTopicNameParameters.builder()
                .resource("dispatch.instance")
                .build();

        requestTopicService.ensureTopic(requestTopicNameParameters, 0, TopicCleanupPolicyParameters.builder().build());

        return instanceFlowRequestConsumerFactoryService.createFactory(
                SakResource.class,
                Status.class,
                consumerRecord -> InstanceFlowReplyProducerRecord.<Status>builder()
                        .instanceFlowHeaders(consumerRecord.getInstanceFlowHeaders().toBuilder().build())
                        .value(statusService.getNextReplyStatus())
                        .build(),
                new CommonLoggingErrorHandler()
        ).createContainer(requestTopicNameParameters);
    }

}

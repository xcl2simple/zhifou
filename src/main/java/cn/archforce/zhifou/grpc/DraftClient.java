package cn.archforce.zhifou.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/5/13 17:29
 * @since JDK 1.8
 */
@Slf4j
public class DraftClient {

    private final ManagedChannel channel;
    private final DraftServiceGrpc.DraftServiceBlockingStub blockingStub;

    public DraftClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build();
        blockingStub = DraftServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public Integer insertAnswerDraft(Long questionId, Long userId, String content) {
        log.info("Will try to insert into draft_answer, params: questionId={}, userId={}, content= {}",questionId, userId, content);
        DraftAnswer draftAnswer = DraftAnswer.newBuilder().setQuestionId(questionId).setUserId(userId)
                .setContent(content).build();
        DraftReply response;

        try {
            response = blockingStub.addAnswerDraft(draftAnswer);
        } catch (StatusRuntimeException e) {
            log.error("RPC failed: {0}", e.getStatus());
            return 0;
        }
        log.info("response: " + response);
        return response.getCode();
    }

}

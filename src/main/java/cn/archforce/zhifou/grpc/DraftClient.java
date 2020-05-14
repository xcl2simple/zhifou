package cn.archforce.zhifou.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Description:
 *
 * @author mochuting
 * @version 1.0
 * @date 2020/5/13 17:29
 * @since JDK 1.8
 */
public class DraftClient {

    private static final Logger logger = Logger.getLogger(DraftClient.class.getName());

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
        logger.info("gRPC will try to insert into draft_answer, param: content= " + content);
        DraftAnswer draftAnswer = DraftAnswer.newBuilder().setQuestionId(questionId).setUserId(userId)
                .setContent(content).build();
        DraftReply response;

        try {
            response = blockingStub.addAnswerDraft(draftAnswer);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return 0;
        }
        logger.info("response: " + response.getMessage());
        return response.getCode();
    }

}

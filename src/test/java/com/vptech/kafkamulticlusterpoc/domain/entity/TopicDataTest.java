package com.vptech.kafkamulticlusterpoc.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for TopicData entity
 *
 * @author david.amigo
 */
@SpringBootTest
public class TopicDataTest {

    @Test
    public void testGetNameReturnsTheNameOfTheTopic() {

        TopicData topicData = new TopicData("_101_");

        Assertions.assertEquals("_101_",  topicData.getName());
    }

    @Test
    public void testGetProducedCountWithNoDataReturns0() {

        TopicData topicData = new TopicData("_test_");

        Assertions.assertEquals(0,  topicData.getProducedCount());
    }

    @Test
    public void testGetProducedCountAfterProducing1MessageReturns1() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageProduced("201");

        Assertions.assertEquals(1,  topicData.getProducedCount());
    }

    @Test
    public void testGetProducedCountAfterProducing2MessagesReturns2() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageProduced("202");
        topicData.messageProduced("203");

        Assertions.assertEquals(2,  topicData.getProducedCount());
    }

    @Test
    public void testGetConsumedCountWithNoDataReturns0() {

        TopicData topicData = new TopicData("_test_");

        Assertions.assertEquals(0,  topicData.getConsumedCount());
    }

    @Test
    public void testGetConsumedCountAfterProducing1MessageReturns1() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageConsumed("301");

        Assertions.assertEquals(1,  topicData.getConsumedCount());
    }

    @Test
    public void testGetConsumedCountAfterProducing2MessagesReturns2() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageConsumed("302");
        topicData.messageConsumed("303");

        Assertions.assertEquals(2,  topicData.getConsumedCount());
    }

    @Test
    public void testGetPendingMessagesCountWithNoDataReturns0() {

        TopicData topicData = new TopicData("_test_");

        Assertions.assertEquals(0,  topicData.getPendingMessagesCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducing1MessageReturns1() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageProduced("401");

        Assertions.assertEquals(1,  topicData.getPendingMessagesCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducing2MessagesReturns2() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageProduced("402");
        topicData.messageProduced("403");

        Assertions.assertEquals(2,  topicData.getPendingMessagesCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducingAndConsumingTheSameMessageReturns0() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageProduced("404");
        topicData.messageConsumed("404");

        Assertions.assertEquals(0,  topicData.getPendingMessagesCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountWithNoDataReturns0() {

        TopicData topicData = new TopicData("_test_");

        Assertions.assertEquals(0,  topicData.getOutOfSeqMessagesCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsuming1MessageReturns1() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageConsumed("501");

        Assertions.assertEquals(1,  topicData.getOutOfSeqMessagesCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsuming2MessagesReturns2() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageConsumed("502");
        topicData.messageConsumed("503");

        Assertions.assertEquals(2,  topicData.getOutOfSeqMessagesCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsumingAndProducingTheSameMessageReturns0() {

        TopicData topicData = new TopicData("_test_");
        topicData.messageConsumed("504");
        topicData.messageProduced("504");

        Assertions.assertEquals(0,  topicData.getOutOfSeqMessagesCount());
    }

    @Test
    public void testGetUnableToProduceCountWithNoDataReturns0() {

        TopicData topicData = new TopicData("_test_");

        Assertions.assertEquals(0,  topicData.getUnableToProduceCount());
    }

    @Test
    public void testGetUnableToProduceCountAfterAnErrorProducing1MessageReturns1() {

        TopicData topicData = new TopicData("_test_");
        topicData.errorProducingMessage("601");

        Assertions.assertEquals(1,  topicData.getUnableToProduceCount());
    }

    @Test
    public void testGetUnableToProduceCountAfter2ErrorsProducingMessagesReturns2() {

        TopicData topicData = new TopicData("_test_");
        topicData.errorProducingMessage("602");
        topicData.errorProducingMessage("603");

        Assertions.assertEquals(2,  topicData.getUnableToProduceCount());
    }
}

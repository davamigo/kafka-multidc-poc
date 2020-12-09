package com.vptech.kafkamulticlusterpoc.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for TopicStatistics entity
 *
 * @author david.amigo
 */
@SpringBootTest
public class TopicStatisticsTest {

    @Test
    public void testGetNameReturnsTheNameOfTheTopic() {

        TopicStatistics stats = new TopicStatistics("_101_");

        Assertions.assertEquals("_101_",  stats.getName());
    }

    @Test
    public void testGetProducedCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0,  stats.getProducedCount());
    }

    @Test
    public void testGetProducedCountAfterProducing1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addProducedMessage("201");

        Assertions.assertEquals(1,  stats.getProducedCount());
    }

    @Test
    public void testGetProducedCountAfterProducing2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addProducedMessage("202");
        stats.addProducedMessage("203");

        Assertions.assertEquals(2,  stats.getProducedCount());
    }

    @Test
    public void testGetConsumedCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0,  stats.getConsumedCount());
    }

    @Test
    public void testGetConsumedCountAfterProducing1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessage("301");

        Assertions.assertEquals(1,  stats.getConsumedCount());
    }

    @Test
    public void testGetConsumedCountAfterProducing2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessage("302");
        stats.addConsumedMessage("303");

        Assertions.assertEquals(2,  stats.getConsumedCount());
    }

    @Test
    public void testGetPendingMessagesCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0,  stats.getPendingMessagesCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducing1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addProducedMessage("401");

        Assertions.assertEquals(1,  stats.getPendingMessagesCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducing2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addProducedMessage("402");
        stats.addProducedMessage("403");

        Assertions.assertEquals(2,  stats.getPendingMessagesCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducingAndConsumingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addProducedMessage("404");
        stats.addConsumedMessage("404");

        Assertions.assertEquals(0,  stats.getPendingMessagesCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0,  stats.getOutOfSeqMessagesCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsuming1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessage("501");

        Assertions.assertEquals(1,  stats.getOutOfSeqMessagesCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsuming2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessage("502");
        stats.addConsumedMessage("503");

        Assertions.assertEquals(2,  stats.getOutOfSeqMessagesCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsumingAndProducingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessage("504");
        stats.addProducedMessage("504");

        Assertions.assertEquals(0,  stats.getOutOfSeqMessagesCount());
    }

    @Test
    public void testGetUnableToProduceCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0,  stats.getUnableToProduceMessagesCount());
    }

    @Test
    public void testGetUnableToProduceCountAfterAnErrorProducing1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addErrorProducingMessage("601");

        Assertions.assertEquals(1,  stats.getUnableToProduceMessagesCount());
    }

    @Test
    public void testGetUnableToProduceCountAfter2ErrorsProducingMessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addErrorProducingMessage("602");
        stats.addErrorProducingMessage("603");

        Assertions.assertEquals(2,  stats.getUnableToProduceMessagesCount());
    }
}

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
        stats.addMessageProducedSuccessfully("201");

        Assertions.assertEquals(1,  stats.getProducedCount());
    }

    @Test
    public void testGetProducedCountAfterProducing2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("202");
        stats.addMessageProducedSuccessfully("203");

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
        stats.addConsumedMessageSuccessfully("301");

        Assertions.assertEquals(1,  stats.getConsumedCount());
    }

    @Test
    public void testGetConsumedCountAfterProducing2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessageSuccessfully("302");
        stats.addConsumedMessageSuccessfully("303");

        Assertions.assertEquals(2,  stats.getConsumedCount());
    }

    @Test
    public void testGetPendingMessagesCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0,  stats.getMessagesProducedNotConsumedCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducing1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("401");

        Assertions.assertEquals(1,  stats.getMessagesProducedNotConsumedCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducing2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("402");
        stats.addMessageProducedSuccessfully("403");

        Assertions.assertEquals(2,  stats.getMessagesProducedNotConsumedCount());
    }

    @Test
    public void testGetPendingMessagesCountAfterProducingAndConsumingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("404");
        stats.addConsumedMessageSuccessfully("404");

        Assertions.assertEquals(0,  stats.getMessagesProducedNotConsumedCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0,  stats.getMessagesConsumedNotProducedCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsuming1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessageSuccessfully("501");

        Assertions.assertEquals(1,  stats.getMessagesConsumedNotProducedCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsuming2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessageSuccessfully("502");
        stats.addConsumedMessageSuccessfully("503");

        Assertions.assertEquals(2,  stats.getMessagesConsumedNotProducedCount());
    }

    @Test
    public void testGetOutOfSeqMessagesCountAfterConsumingAndProducingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addConsumedMessageSuccessfully("504");
        stats.addMessageProducedSuccessfully("504");

        Assertions.assertEquals(0,  stats.getMessagesConsumedNotProducedCount());
    }

    @Test
    public void testGetUnableToProduceCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0,  stats.getMessagesNotProducedBecauseAnErrorCount());
    }

    @Test
    public void testGetUnableToProduceCountAfterAnErrorProducing1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageNotProduced("601");

        Assertions.assertEquals(1,  stats.getMessagesNotProducedBecauseAnErrorCount());
    }

    @Test
    public void testGetUnableToProduceCountAfter2ErrorsProducingMessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageNotProduced("602");
        stats.addMessageNotProduced("603");

        Assertions.assertEquals(2,  stats.getMessagesNotProducedBecauseAnErrorCount());
    }
}

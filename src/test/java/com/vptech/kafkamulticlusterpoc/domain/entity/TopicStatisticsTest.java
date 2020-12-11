package com.vptech.kafkamulticlusterpoc.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

        Assertions.assertEquals("_101_", stats.getName());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetProducedCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0, stats.getProducedCount());
    }

    @Test
    public void testGetProducedCountAfterProducing1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("201");

        Assertions.assertEquals(1, stats.getProducedCount());
    }

    @Test
    public void testGetProducedCountAfterProducing2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("202");
        stats.addMessageProducedSuccessfully("203");

        Assertions.assertEquals(2, stats.getProducedCount());
    }

    @Test
    public void testGetProducedCountAfterProducing2TimesTheSameMessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("204");
        stats.addMessageProducedSuccessfully("204");

        Assertions.assertEquals(1, stats.getProducedCount());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetConsumedCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0, stats.getConsumedCount());
    }

    @Test
    public void testGetConsumedCountAfterConsuming1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("301");

        Assertions.assertEquals(1, stats.getConsumedCount());
    }

    @Test
    public void testGetConsumedCountAfterConsuming2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("302");
        stats.addMessageConsumedSuccessfully("303");

        Assertions.assertEquals(2, stats.getConsumedCount());
    }

    @Test
    public void testGetConsumedCountAfterConsuming2TimesTheSameMessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("304");
        stats.addMessageConsumedSuccessfully("304");

        Assertions.assertEquals(1, stats.getConsumedCount());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetMessagesSentToProducerCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0, stats.getMessagesSentToProducerCount());
    }

    @Test
    public void testGetMessagesSentToProducerCountAfterSending1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("401");

        Assertions.assertEquals(1, stats.getMessagesSentToProducerCount());
    }

    @Test
    public void testGetMessagesSentToProducerCountAfterSending2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("402");
        stats.addMessageSentToBeProduced("403");

        Assertions.assertEquals(2, stats.getMessagesSentToProducerCount());
    }

    @Test
    public void testGetMessagesSentToProducerCountAfterSending2TimesTheSameMessagesReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("404");
        stats.addMessageSentToBeProduced("404");

        Assertions.assertEquals(1, stats.getMessagesSentToProducerCount());
    }

    @Test
    public void testGetMessagesSentToProducerCountAfterSendingAndProducingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("405");
        stats.addMessageProducedSuccessfully("405");

        Assertions.assertEquals(0, stats.getMessagesSentToProducerCount());
    }

    @Test
    public void testGetMessagesSentToProducerCountAfterSendingAndConsumingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("406");
        stats.addMessageConsumedSuccessfully("406");

        Assertions.assertEquals(0, stats.getMessagesSentToProducerCount());
    }

    @Test
    public void testGetMessagesSentToProducerCountAfterSending1MessageAndErrorProducingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("407");
        stats.addMessageNotProduced("407");

        Assertions.assertEquals(0, stats.getMessagesSentToProducerCount());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetMessagesSentToProducerWithNoDataReturnsAnEmptyList() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertTrue(stats.getMessagesSentToProducer().isEmpty());
    }

    @Test
    public void testGetMessagesSentToProducerAfterSending1MessageReturnsAListWithOneMessage() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("411");

        List<String> expected = Collections.singletonList("411");

        Assertions.assertEquals(expected, stats.getMessagesSentToProducer());
    }

    @Test
    public void testGetMessagesSentToProducerAfterSending2MessagesReturnsAListWithTwoMessages() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("412");
        stats.addMessageSentToBeProduced("413");

        List<String> expected = Arrays.asList("412", "413");

        Assertions.assertEquals(expected, stats.getMessagesSentToProducer());
    }

    @Test
    public void testGetMessagesSentToProducerAfterSending2TimesTheSameMessageMessagesReturnsAListWithOneMessages() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageSentToBeProduced("414");
        stats.addMessageSentToBeProduced("414");

        List<String> expected = Collections.singletonList("414");

        Assertions.assertEquals(expected, stats.getMessagesSentToProducer());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetMessagesProducedNotConsumedCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0, stats.getMessagesProducedNotConsumedCount());
    }

    @Test
    public void testGetMessagesProducedNotConsumedCountAfterProducing1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("501");

        Assertions.assertEquals(1, stats.getMessagesProducedNotConsumedCount());
    }

    @Test
    public void testGetMessagesProducedNotConsumedCountAfterProducing2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("502");
        stats.addMessageProducedSuccessfully("503");

        Assertions.assertEquals(2, stats.getMessagesProducedNotConsumedCount());
    }

    @Test
    public void testGetMessagesProducedNotConsumedCountAfterProducingAndConsumingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("504");
        stats.addMessageConsumedSuccessfully("504");

        Assertions.assertEquals(0, stats.getMessagesProducedNotConsumedCount());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetMessagesProducedNotConsumedWithNoDataReturnsAnEmptyList() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertTrue(stats.getMessagesProducedNotConsumed().isEmpty());
    }

    @Test
    public void testGetMessagesProducedNotConsumedAfterSending1MessageReturnsAListWithOneMessage() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("511");

        List<String> expected = Collections.singletonList("511");

        Assertions.assertEquals(expected, stats.getMessagesProducedNotConsumed());
    }

    @Test
    public void testGetMessagesProducedNotConsumedAfterSending2MessagesReturnsAListWithTwoMessages() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("512");
        stats.addMessageProducedSuccessfully("513");

        List<String> expected = Arrays.asList("512", "513");

        Assertions.assertEquals(expected, stats.getMessagesProducedNotConsumed());
    }

    @Test
    public void testGetMessagesProducedNotConsumedAfterSending2TimesTheSameMessageMessagesReturnsAListWithOneMessages() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageProducedSuccessfully("514");
        stats.addMessageProducedSuccessfully("514");

        List<String> expected = Collections.singletonList("514");

        Assertions.assertEquals(expected, stats.getMessagesProducedNotConsumed());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetMessagesConsumedNotProducedCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0, stats.getMessagesConsumedNotProducedCount());
    }

    @Test
    public void testGetMessagesConsumedNotProducedCountAfterConsuming1MessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("601");

        Assertions.assertEquals(1, stats.getMessagesConsumedNotProducedCount());
    }

    @Test
    public void testGetMessagesConsumedNotProducedCountAfterConsuming2MessagesReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("602");
        stats.addMessageConsumedSuccessfully("603");

        Assertions.assertEquals(2, stats.getMessagesConsumedNotProducedCount());
    }

    @Test
    public void testGetMessagesConsumedNotProducedCountAfterConsumingAndProducingTheSameMessageReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("604");
        stats.addMessageProducedSuccessfully("604");

        Assertions.assertEquals(0, stats.getMessagesConsumedNotProducedCount());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetMessagesConsumedNotProducedWithNoDataReturnsAnEmptyList() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertTrue(stats.getMessagesConsumedNotProduced().isEmpty());
    }

    @Test
    public void testGetMessagesConsumedNotProducedAfterSending1MessageReturnsAListWithOneMessage() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("611");

        List<String> expected = Collections.singletonList("611");

        Assertions.assertEquals(expected, stats.getMessagesConsumedNotProduced());
    }

    @Test
    public void testGetMessagesConsumedNotProducedAfterSending2MessagesReturnsAListWithTwoMessages() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("612");
        stats.addMessageConsumedSuccessfully("613");

        List<String> expected = Arrays.asList("612", "613");

        Assertions.assertEquals(expected, stats.getMessagesConsumedNotProduced());
    }

    @Test
    public void testGetMessagesConsumedNotProducedAfterSending2TimesTheSameMessageMessagesReturnsAListWithOneMessages() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageConsumedSuccessfully("614");
        stats.addMessageConsumedSuccessfully("614");

        List<String> expected = Collections.singletonList("614");

        Assertions.assertEquals(expected, stats.getMessagesConsumedNotProduced());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetMessagesNotProducedBecauseAnErrorCountWithNoDataReturns0() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertEquals(0, stats.getMessagesNotProducedBecauseAnErrorCount());
    }

    @Test
    public void testGetMessagesNotProducedBecauseAnErrorCountAfter1ErrorReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageNotProduced("701");

        Assertions.assertEquals(1, stats.getMessagesNotProducedBecauseAnErrorCount());
    }

    @Test
    public void testGetMessagesNotProducedBecauseAnErrorCountAfter2ErrorsReturns2() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageNotProduced("702");
        stats.addMessageNotProduced("703");

        Assertions.assertEquals(2, stats.getMessagesNotProducedBecauseAnErrorCount());
    }

    @Test
    public void testGetMessagesNotProducedBecauseAnErrorCountAfter2ErrorSameMessageReturns1() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageNotProduced("704");
        stats.addMessageNotProduced("704");

        Assertions.assertEquals(1, stats.getMessagesNotProducedBecauseAnErrorCount());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetMessagesNotProducedBecauseAnErrorWithNoDataReturnsAnEmptyList() {

        TopicStatistics stats = new TopicStatistics("_test_");

        Assertions.assertTrue(stats.getMessagesNotProducedBecauseAnError().isEmpty());
    }

    @Test
    public void testGetMessagesNotProducedBecauseAnErrorAfterSending1MessageReturnsAListWithOneMessage() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageNotProduced("711");

        List<String> expected = Collections.singletonList("711");

        Assertions.assertEquals(expected, stats.getMessagesNotProducedBecauseAnError());
    }

    @Test
    public void testGetMessagesNotProducedBecauseAnErrorAfterSending2MessagesReturnsAListWithTwoMessages() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageNotProduced("712");
        stats.addMessageNotProduced("713");

        List<String> expected = Arrays.asList("712", "713");

        Assertions.assertEquals(expected, stats.getMessagesNotProducedBecauseAnError());
    }

    @Test
    public void testGetMessagesNotProducedBecauseAnErrorAfterSending2TimesTheSameMessageMessagesReturnsAListWithOneMessages() {

        TopicStatistics stats = new TopicStatistics("_test_");
        stats.addMessageNotProduced("714");
        stats.addMessageNotProduced("714");

        List<String> expected = Collections.singletonList("714");

        Assertions.assertEquals(expected, stats.getMessagesNotProducedBecauseAnError());
    }
}

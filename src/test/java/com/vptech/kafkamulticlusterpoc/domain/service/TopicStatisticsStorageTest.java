package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.TopicStatistics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for TopicStatisticsStorage service
 *
 * @author david.amigo
 */
@SpringBootTest
public class TopicStatisticsStorageTest {

    @Test
    public void testGetTopicStatisticsOfAnExistingTopicReturnsTheTopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_101_");
        TopicStatistics statistics = storage.getTopicStatistics("_topic_101_");

        Assertions.assertNotNull(statistics);
        Assertions.assertEquals("_topic_101_", statistics.getName());
    }

    @Test
    public void testGetTopicStatisticsOfNonExistingTopicReturnsNull() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        TopicStatistics statistics = storage.getTopicStatistics("_topic_102_");

        Assertions.assertNull(statistics);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetAllTopicStatisticsOfAnEmptyObjetReturnsAnEmptyList() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertTrue(storage.getAllTopicStatistics().isEmpty());
    }

    @Test
    public void testGetAllTopicStatisticsAfterAdding1TopicReturnsAListOf1TopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_201_");

        Assertions.assertEquals(1, storage.getAllTopicStatistics().size());
    }

    @Test
    public void testGetAllTopicStatisticsAfterAddingTwoTimesTheSameTopicReturnsAListOf1TopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_202_");
        storage.addTopic("_topic_202_");

        Assertions.assertEquals(1, storage.getAllTopicStatistics().size());
    }

    @Test
    public void testGetAllTopicStatisticsAfterAdding2TopicsReturnsAListOf2TopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_203_");
        storage.addTopic("_topic_204_");

        Assertions.assertEquals(2, storage.getAllTopicStatistics().size());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testGetAllTopicsOfAnEmptyObjetReturnsAnEmptyList() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertTrue(storage.getAllTopics().isEmpty());
    }

    @Test
    public void testGetAllTopicsAfterAdding1TopicReturnsAListOf1Topic() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_211_");

        Assertions.assertEquals(1, storage.getAllTopics().size());
    }

    @Test
    public void testGetAllTopicsAfterAddingTwoTimesTheSameTopicReturnsAListOf1Topic() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_212_");
        storage.addTopic("_topic_212_");

        Assertions.assertEquals(1, storage.getAllTopics().size());
    }

    @Test
    public void testGetAllTopicsAfterAdding2TopicsReturnsAListOf2Topics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_213_");
        storage.addTopic("_topic_214_");

        Assertions.assertEquals(2, storage.getAllTopics().size());
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddMessageSentToBeProducedOfAnExistingTopicAddsTheDataToTheTopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_301_");
        storage.addMessageSentToBeProduced("_topic_301_", "_some_payload_");

        Assertions.assertEquals(1, storage.getTopicStatistics("_topic_301_").getMessagesSentToProducerCount());
    }

    @Test
    public void testAddMessageSentToBeProducedOfANonExistingTopicCausesAnException() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> storage.addMessageSentToBeProduced("_topic_302_", "_some_payload_")
        );
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddMessageProducedSuccessfullyOfAnExistingTopicAddsTheDataToTheTopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_401_");
        storage.addMessageProducedSuccessfully("_topic_401_", "_some_payload_");

        Assertions.assertEquals(1, storage.getTopicStatistics("_topic_401_").getProducedCount());
    }

    @Test
    public void testAddMessageProducedSuccessfullyOfANonExistingTopicCausesAnException() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> storage.addMessageProducedSuccessfully("_topic_402_", "_some_payload_")
        );
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddMessageConsumedSuccessfullyOfAnExistingTopicAddsTheDataToTheTopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_501_");
        storage.addMessageConsumedSuccessfully("_topic_501_", "_some_payload_");

        Assertions.assertEquals(1, storage.getTopicStatistics("_topic_501_").getConsumedCount());
    }

    @Test
    public void testAddMessageConsumedSuccessfullyOfANonExistingTopicCausesAnException() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> storage.addMessageConsumedSuccessfully("_topic_502_", "_some_payload_")
        );
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testAddMessageNotProducedOfAnExistingTopicAddsTheDataToTheTopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_601_");
        storage.addMessageNotProduced("_topic_601_", "_some_payload_");

        Assertions.assertEquals(1, storage.getTopicStatistics("_topic_601_").getMessagesNotProducedBecauseAnErrorCount());
    }

    @Test
    public void testAddMessageNotProducedOfANonExistingTopicCausesAnException() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> storage.addMessageNotProduced("_topic_602_", "_some_payload_")
        );
    }
}

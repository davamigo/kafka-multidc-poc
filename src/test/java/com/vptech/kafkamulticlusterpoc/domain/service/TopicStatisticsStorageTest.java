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
    public void testGetTopicStatisticsOfAnExistingTopicReturnsTheTopicSttistics() {

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

    @Test
    public void testAddProducedMessageOfAnExistingTopicAddsTheDataToTheTopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_301_");
        storage.addProducedMessage("_topic_301_", "_some_payload_");

        Assertions.assertEquals(1, storage.getTopicStatistics("_topic_301_").getProducedCount());
    }

    @Test
    public void testAddProducedMessageOfANonExistingTopicCausesAnException() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> storage.addProducedMessage("_topic_302_", "_some_payload_")
        );
    }

    @Test
    public void testAddConsumedMessageOfAnExistingTopicAddsTheDataToTheTopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_401_");
        storage.addConsumedMessage("_topic_401_", "_some_payload_");

        Assertions.assertEquals(1, storage.getTopicStatistics("_topic_401_").getConsumedCount());
    }

    @Test
    public void testAddConsumedMessageOfANonExistingTopicCausesAnException() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> storage.addConsumedMessage("_topic_402_", "_some_payload_")
        );
    }

    @Test
    public void testAddErrorProducingMessageOfAnExistingTopicAddsTheDataToTheTopicStatistics() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();
        storage.addTopic("_topic_501_");
        storage.addErrorProducingMessage("_topic_501_", "_some_payload_");

        Assertions.assertEquals(1, storage.getTopicStatistics("_topic_501_").getUnableToProduceCount());
    }

    @Test
    public void testAddErrorProducingMessageOfANonExistingTopicCausesAnException() {

        TopicStatisticsStorage storage = new TopicStatisticsStorage();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> storage.addErrorProducingMessage("_topic_502_", "_some_payload_")
        );
    }
}

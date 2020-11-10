package com.vptech.kafkamulticlusterpoc.domain.service;

import com.vptech.kafkamulticlusterpoc.domain.entity.KafkaBrokerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ServerData;
import com.vptech.kafkamulticlusterpoc.domain.entity.ZookeeperData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Unit test for ServerDataBuilder service
 *
 * @author david.amigo
 */
@SpringBootTest
public class ServerDataBuilderTest {

    @Test
    public void testBuildWithNullTypeReturnsNull() {

        ServerDataBuilder builder = new ServerDataBuilder();

        Assertions.assertNull(builder.build("NULL"));
    }

    @Test
    public void testBuildWithNullReturnsNull() {

        ServerDataBuilder builder = new ServerDataBuilder();

        Assertions.assertNull(builder.build(null));
    }

    @Test
    public void testBuildWithInvalidTypeReturnsNull() {

        ServerDataBuilder builder = new ServerDataBuilder();

        Assertions.assertNull(builder.build("invalid"));
    }

    @Test
    public void testBuildWithZookeeperTypeReturnsNewZookeeperDataObject() {

        ServerDataBuilder builder = new ServerDataBuilder();

        ServerData result = builder.build("ZOOKEEPER");

        Assertions.assertTrue(result instanceof ZookeeperData);
    }

    @Test
    public void testBuildWithKafkaBrokerTypeReturnsNewKafkaBrokerDataObject() {

        ServerDataBuilder builder = new ServerDataBuilder();

        ServerData result = builder.build("KAFKA_BROKER");

        Assertions.assertTrue(result instanceof KafkaBrokerData);
    }
}

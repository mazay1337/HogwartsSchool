package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@Service
public class InfoService {

    private final int port;
    private final Logger logger = LoggerFactory.getLogger(InfoService.class);

    public InfoService(@Value("${server.port}") int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void parallel() {
        // (1 + 1_000_000) * 1_000_000 / 2

        StopWatch stopWatch = new StopWatch();
        //1
        stopWatch.start("sequential stream");
        long sum = Stream.iterate(1L, a -> a + 1)
                .limit(1_000_000)
                .reduce(0L, Long::sum);
        stopWatch.stop();

        //2
        stopWatch.start("parallel stream");
        sum = Stream.iterate(1L, a -> a + 1).parallel()
                .limit(1_000_000)
                .reduce(0L, Long::sum);
        stopWatch.stop();

        //3
        stopWatch.start("parallel-2 stream");
        sum = LongStream.rangeClosed(1, 1000000).parallel().sum();
        stopWatch.stop();

        //4
        stopWatch.start("stream");
        sum = LongStream.rangeClosed(1, 1000000).sum();
        stopWatch.stop();

        logger.info(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }
}
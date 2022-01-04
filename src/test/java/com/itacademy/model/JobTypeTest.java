package com.itacademy.model;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JobTypeTest {

    @Test
    void testGivenExistentJobWhenExistsJobThenReturnTrue() {
        String junior = "junior";
        String middle = "middle";
        String senior = "senior";
        String manager = "manager";
        String owner = "owner";

        assertThat(JobType.existsJob(junior), is(true));
        assertThat(JobType.existsJob(middle), is(true));
        assertThat(JobType.existsJob(senior), is(true));
        assertThat(JobType.existsJob(manager), is(true));
        assertThat(JobType.existsJob(owner), is(true));
    }

    @Test
    void testGivenNotExistentJobWhenExistsJobThenReturnFalse() {
        String notExistentJob = "NULL";

        assertThat(JobType.existsJob(notExistentJob), is(false));
    }

}

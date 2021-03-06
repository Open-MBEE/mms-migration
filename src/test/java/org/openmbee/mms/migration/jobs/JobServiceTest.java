package org.openmbee.mms.migration.jobs;

import org.junit.Test;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JobServiceTest {

    @Test
    public void testOneJob() {
        ApplicationContext context = mock(ApplicationContext.class);
        Job job1 = mock(Job.class);
        when(context.getBean("ONE_job", Job.class)).thenReturn(job1);

        ApplicationArguments arguments = mock(ApplicationArguments.class);
        when(arguments.getSourceArgs()).thenReturn(new String[]{"-blah", "-jobs=ONE"});

        JobService jobService = new JobService();
        jobService.setApplicationContext(context);
        jobService.setApplicationArguments(arguments);

        List<Job> jobs = jobService.getJobs();

        assertNotNull(jobs);
        assertEquals(1, jobs.size());
        assertSame(job1, jobs.get(0));
    }

    @Test
    public void testMultipleJobs() {
        ApplicationContext context = mock(ApplicationContext.class);
        Job job1 = mock(Job.class);
        Job job2 = mock(Job.class);
        Job job3 = mock(Job.class);
        when(context.getBean("ONE_job", Job.class)).thenReturn(job1);
        when(context.getBean("TWO_job", Job.class)).thenReturn(job2);
        when(context.getBean("THREE_job", Job.class)).thenReturn(job3);

        ApplicationArguments arguments = mock(ApplicationArguments.class);
        when(arguments.getSourceArgs()).thenReturn(new String[]{"-blah", "-jobs=ONE|TWO|THREE"});

        JobService jobService = new JobService();
        jobService.setApplicationContext(context);
        jobService.setApplicationArguments(arguments);

        List<Job> jobs = jobService.getJobs();

        assertNotNull(jobs);
        assertEquals(3, jobs.size());
        assertSame(job1, jobs.get(0));
        assertSame(job2, jobs.get(1));
        assertSame(job3, jobs.get(2));
    }

    @Test
    public void testCantFindJob() {
        ApplicationContext context = mock(ApplicationContext.class);
        Job job1 = mock(Job.class);
        Job job2 = mock(Job.class);
        when(context.getBean("ONE_job", Job.class)).thenReturn(job1);
        when(context.getBean("TWO_job", Job.class)).thenReturn(job2);
        when(context.getBean("THREE_job", Job.class)).thenThrow(new RuntimeException());

        ApplicationArguments arguments = mock(ApplicationArguments.class);
        when(arguments.getSourceArgs()).thenReturn(new String[]{"-blah", "-jobs=ONE|TWO|THREE"});

        JobService jobService = new JobService();
        jobService.setApplicationContext(context);
        jobService.setApplicationArguments(arguments);

        try {
            List<Job> jobs = jobService.getJobs();
            fail();
        } catch(Exception ex) {

        }
    }

    @Test
    public void testEmptyJobs() {
        ApplicationContext context = mock(ApplicationContext.class);

        ApplicationArguments arguments = mock(ApplicationArguments.class);
        when(arguments.getSourceArgs()).thenReturn(new String[]{"-blah", "-jobs="});

        JobService jobService = new JobService();
        jobService.setApplicationContext(context);
        jobService.setApplicationArguments(arguments);

        try {
            List<Job> jobs = jobService.getJobs();
            fail();
        } catch(Exception ex) {

        }
    }

    @Test
    public void testNoJobs() {
        ApplicationContext context = mock(ApplicationContext.class);

        ApplicationArguments arguments = mock(ApplicationArguments.class);
        when(arguments.getSourceArgs()).thenReturn(new String[]{"-blah"});

        JobService jobService = new JobService();
        jobService.setApplicationContext(context);
        jobService.setApplicationArguments(arguments);

        try {
            List<Job> jobs = jobService.getJobs();
            fail();
        } catch(Exception ex) {

        }
    }

}
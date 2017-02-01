package by.bsuir.movierating.dao;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-dao-context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DatabaseSetup("/dao/CommentDaoTest.xml")
public class CommentDaoTest {
    @Autowired
    private CommentDAO commentDAO;

    @Test
    @ExpectedDatabase(value = "/dao/CommentDaoTest.testAddComment-result.xml", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    public void testAddComment() {

    }
}

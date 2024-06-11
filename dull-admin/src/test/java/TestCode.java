import com.dullfan.AdminApplication;
import com.dullfan.common.utils.SecurityUtils;
import com.dullfan.system.domain.po.Config;
import com.dullfan.system.domain.query.ConfigQuery;
import com.dullfan.system.service.ConfigService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = AdminApplication.class)
public class TestCode {
    @Resource
    private ConfigService configService;

    @Test
    public void testCode() {

    }
}

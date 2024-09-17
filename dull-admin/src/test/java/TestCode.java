import com.dullfan.AdminApplication;
import com.dullfan.common.utils.encryption.IdUtil;
import com.dullfan.system.service.ConfigService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

@SpringBootTest(classes = AdminApplication.class)
public class TestCode {

    @Resource
    private ConfigService configService;

    @Test
    public void testCode() {

    }

}

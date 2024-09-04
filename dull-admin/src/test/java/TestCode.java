import com.dullfan.AdminApplication;
import com.dullfan.common.utils.encryption.IdUtil;
import com.dullfan.common.utils.uuid.IdUtils;
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
        String encrypt = IdUtil.encrypt(52567412552L);
        Long decrypt = IdUtil.decrypt(encrypt);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}

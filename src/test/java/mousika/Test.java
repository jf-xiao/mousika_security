/**
 * 
 */
package mousika;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author xiaojf 294825811@qq.com
 */
public class Test {

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder(36).encode("jimi"));
    }

}

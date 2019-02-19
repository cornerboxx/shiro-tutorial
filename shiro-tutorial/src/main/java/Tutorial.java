import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class aaa{
    public void info(String a){
        System.out.println(a);
    }
}

public class Tutorial {

//    private static final transient Logger log = LoggerFactory.getLogger(Tutorial.class);
    private static final aaa log = new aaa();

    public static void main(String[] args) {
        System.out.println("fff");

        log.info("My First Apache Shiro Application");

        Ini ini = new Ini();
        //populate the Ini instance as necessary

        //1.
        Factory<SecurityManager> factory = new IniSecurityManagerFactory(ini);
//        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        //2.
        SecurityManager securityManager = factory.getInstance();

        //3.
        SecurityUtils.setSecurityManager(securityManager);


        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute( "someKey", "aValue" );

        if ( !currentUser.isAuthenticated() ) {
            //collect user principals and credentials in a gui specific manner
            //such as username/password html form, X509 certificate, OpenID, etc.
            //We'll use the username/password example here since it is the most common.
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");

            //this is all you have to do to support 'remember me' (no config - built in!):
            token.setRememberMe(true);

            try {
                currentUser.login(token);
                //if no exception, that's it, we're done!

                //print their identifying principal (in this case, a username):
                log.info( "User [" + currentUser.getPrincipal() + "] logged in successfully." );
            } catch (UnknownAccountException uae) {
                //username wasn't in the system, show them an error message?
            } catch (IncorrectCredentialsException ice) {
                //password didn't match, try again?
            } catch (LockedAccountException lae) {
                //account for that username is locked - can't login.  Show them a message?
            }
            //... more types exceptions to check if you want ...
            catch (AuthenticationException ae) {
                //unexpected condition - error?
            }

        }

        if ( currentUser.hasRole( "schwartz" ) ) {
            log.info("May the Schwartz be with you!" );
        } else {
            log.info( "Hello, mere mortal." );
        }

        //此外，我们可以执行极其强大的实例级权限检查 - 能够查看用户是否能够访问类型的特定实例：
        if ( currentUser.isPermitted( "winnebago:drive:eagle5" ) ) {
            log.info("You are permitted to 'drive' the 'winnebago' with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        currentUser.logout(); //removes all identifying information and invalidates their session too.

        System.exit(0);
    }
}
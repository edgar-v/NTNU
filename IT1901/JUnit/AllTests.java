import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ TestAddUser.class , TestCheckuser.class, TestRegSheep.class, TestEditSheep.class, TestEditUser.class, TestGetLog.class, TestGetSheepInfo.class, TestGetUserInfo.class, TestLogin.class, TestRecovery.class, TestSheepInfo.class, TestUpdate.class, TestUpdateSheep.class})
public class AllTests {
	
}

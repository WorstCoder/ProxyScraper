package scraper.assigner;

import org.junit.BeforeClass;
import scraper.main.TestsUtils;

public class NonCheckAssignerTest {
	
	
	private static IAssigner assigner;
	
	@BeforeClass
	public static void setUp() {
		assigner = new NonCheckAssigner(TestsUtils.methodFinder);
	}
	
	
	/*@Test
	public void getType() throws Exception {
		ScrapeType type = assigner.getType(TestsUtils.cssSite);
		assertEquals(type, ScrapeType.CSS);
	}
	
	@Test
	public void getProxy() throws Exception {
		List<Proxy> proxies = assigner.getProxy();
		assertTrue(!proxies.isEmpty());
	}*/
	
}
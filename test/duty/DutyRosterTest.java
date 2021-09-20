package duty;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DutyRosterTest {

	@Test
	public void test() throws Exception {
		DutyRoster dr = DutyRoster.create("2021-01-10", "2021-01-18");
		dr.addStaff("zhang-san", "sceretary", "10000001");
		dr.addStaff("zhang-si", "sceretary", "10000002");
		dr.addStaff("zhang-wu", "sceretary", "10000003");
		dr.addStaff("zhang-liu", "sceretary", "10000004");
		dr.addStaff("zhang-qi", "sceretary", "10000005");
		dr.addStaff("zhang-ba", "sceretary", "10000006");
		dr.arrange_automatically();
		assertEquals(
				"duty time: 2021-01-10 -- 2021-01-10	Employee [name=zhang-si, position=sceretary, phone=10000002]\n"
						+ "duty time: 2021-01-11 -- 2021-01-11	Employee [name=zhang-qi, position=sceretary, phone=10000005]\n"
						+ "duty time: 2021-01-12 -- 2021-01-12	Employee [name=zhang-liu, position=sceretary, phone=10000004]\n"
						+ "duty time: 2021-01-13 -- 2021-01-13	Employee [name=zhang-ba, position=sceretary, phone=10000006]\n"
						+ "duty time: 2021-01-14 -- 2021-01-14	Employee [name=zhang-san, position=sceretary, phone=10000001]\n"
						+ "duty time: 2021-01-15 -- 2021-01-18	Employee [name=zhang-wu, position=sceretary, phone=10000003]\n"
						+ "\n" + "The duty time has been fully arranged!\n",
				dr.toString());
	}

	@Test
	public void test2() throws Exception {
		DutyRoster dr = DutyRoster.create("2021-01-10", "2021-01-17");
		dr.addStaff("zhang-san", "sceretary", "10000001");
		dr.addStaff("zhang-si", "sceretary", "10000002");
		dr.addStaff("zhang-wu", "sceretary", "10000003");
		dr.addStaff("zhang-liu", "sceretary", "10000004");
		dr.addStaff("zhang-qi", "sceretary", "10000005");
		dr.addStaff("zhang-ba", "sceretary", "10000006");
		dr.arrange_manually("zhang-san", "sceretary", "10000001", "2021-01-11", 3);
		assertEquals(
				"duty time: 2021-01-11 -- 2021-01-13	Employee [name=zhang-san, position=sceretary, phone=10000001]\n"
						+ "\n" + "No arranged interval: \n" + "2021-01-10 -- 2021-01-10\n"
						+ "2021-01-14 -- 2021-01-17\n" + "\n" + "Blank ratio: 0.625\n",
				dr.toString());
	}

	@Test
	public void test3() throws Exception {
		DutyRoster dr = DutyRoster.create("2021-01-10", "2021-01-18");
		dr.addStaff("zhang-san", "sceretary", "10000001");
		dr.addStaff("zhang-si", "sceretary", "10000002");
		dr.addStaff("zhang-wu", "sceretary", "10000003");
		dr.addStaff("zhang-liu", "sceretary", "10000004");
		dr.addStaff("zhang-qi", "sceretary", "10000005");
		dr.addStaff("zhang-ba", "sceretary", "10000006");
		dr.arrange_manually("zhang-san", "sceretary", "10000001", "2021-01-10", 2);
		System.out.print(dr.toString());
		dr.arrange_manually("zhang-wu", "sceretary", "10000003", "2021-01-14", 1);
		System.out.print(dr.toString());
		dr.arrange_manually("zhang-ba", "sceretary", "10000006" , "2021-01-16", 1);
		System.out.print(dr.toString());
		dr.display();
	}
}

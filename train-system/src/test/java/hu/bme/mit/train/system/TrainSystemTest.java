package hu.bme.mit.train.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;
import hu.bme.mit.train.tachograph.TachoGraphImpl;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	TachoGraphImpl tachograph;
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();
		tachograph = system.getTachograpgh();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void OverridingJoystickPosition_IncreasesReferenceSpeed() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingJoystickPositionToNegative_SetsReferenceSpeedToZero() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void OverridingSensorSpeedlimit() {
		sensor.overrideSpeedLimit(50);
		Assert.assertEquals(50, sensor.getSpeedLimit());
	}
	
	@Test
	public void OverridingSensorSpeedlimit_multipletimes() {
		sensor.overrideSpeedLimit(20);
		sensor.overrideSpeedLimit(40);
		Assert.assertEquals(40, sensor.getSpeedLimit());
	}
	
	@Test
	public void OverridingSensorSpeedlimit_multipletime() {
		sensor.overrideSpeedLimit(20);
		Assert.assertEquals(20, sensor.getSpeedLimit());
		sensor.overrideSpeedLimit(40);
		Assert.assertNotEquals(20, sensor.getSpeedLimit());

	}
	
	@Test
	public void PuttingDatainTheTachograph() {
		tachograph.setRecords(Integer.valueOf(10), Integer.valueOf(user.getJoystickPosition()), Integer.valueOf(controller.getReferenceSpeed()));
		Assert.assertEquals(1, tachograph.getRecords().size());
	}
	
}

package games.stendhal.server.entity.creature;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

//import games.stendhal.client.entity.Item;
//import games.stendhal.server.entity.Entity;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import games.stendhal.server.entity.item.Item;


public class PotionGuzzlingPetsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
	}

	
	
	//Testing if pet heals when HP is 100.
	//Pet should NOT heal in this case.
	@Test
	public void HPTestIs100() {		
		//Defined the player who will be assigned to pet.
		final Player thejoker = PlayerTestHelper.createPlayer("thejoker");
		
		//Defined the Zone where the pet and potion will be placed.
		final StendhalRPZone zone = new StendhalRPZone("zonetest1");
		SingletonRepository.getRPWorld().addRPZone(zone);
		
		//Place the Player in the world.
		zone.add(thejoker);
		
		//Created a new instance of a Cat, assigned it to an owner and placed it in the world.
		final Cat testpet = new Cat();
		testpet.setOwner(thejoker);
		zone.add(testpet);
	
		//Created the item potion and placed it in the world.
		final Item makepotion = SingletonRepository.getEntityManager().getItem("potion");
		assertNotNull(makepotion);
		zone.add(makepotion);
		
		//Pet HP is set to 100.
		testpet.setHP(100);
		
		//LogicHealing should return false in this case,
		//ie the pet shouldn't drink the potion.
		testpet.logic();
		assertTrue(testpet.getHP() == 100);
	}


	
	//Testing if pet heals when HP is > 100.
	//Pet should NOT heal in this case.
	@Test
	public void HPTestMoreThan100() {
		//Defined the player who will be assigned to pet.
		final Player thejoker = PlayerTestHelper.createPlayer("thejoker");
		
		//Defined the Zone where the pet and potion will be placed.
		final StendhalRPZone zone = new StendhalRPZone("zonetest2");
		SingletonRepository.getRPWorld().addRPZone(zone);
		
		//Place the Player in the world.
		zone.add(thejoker);
		
		//Created a new instance of a Cat, assigned it to an owner and placed it in the world.
		final Cat testpet = new Cat();
		testpet.setOwner(thejoker); 
		zone.add(testpet);
	
		//Created the item potion and placed it in the world.
		final Item makepotion = SingletonRepository.getEntityManager().getItem("potion");
		assertNotNull(makepotion);
		zone.add(makepotion);
		
		//Pet HP is set to 200.
		testpet.setHP(150);
		
		//LogicHealing should return false in this case,
		//ie the pet shouldn't drink the potion.
		testpet.logic();
		assertTrue(testpet.getHP() == 150);
	}

	
	
	//Testing if pet heals when HP is < 100.
	//Pet should heal in this case.	
	@Test
	public void HPTestLessThan100() {
		//Defined the player who will be assigned to pet.
		final Player thejoker = PlayerTestHelper.createPlayer("thejoker");
		
		//Defined the Zone where the pet and potion will be placed.
		final StendhalRPZone zone = new StendhalRPZone("zonetest3");		
		SingletonRepository.getRPWorld().addRPZone(zone);
		
		//Place the Player in the world.
		zone.add(thejoker);
		
		//Created a new instance of a Cat, assigned it to an owner and placed it in the world.
		final Cat testpet = new Cat();
		testpet.setOwner(thejoker);
		zone.add(testpet);
	
		//Created the item potion and placed it in the world.
		final Item makepotion = SingletonRepository.getEntityManager().getItem("potion");
		assertNotNull(makepotion);
		zone.add(makepotion);
		
		//Pet HP is set to 50.
		testpet.setHP(50);
		
		//LogicHealing should return True in this case,
		//ie the pet should drink the potion.
		testpet.logic();
		assertFalse(testpet.getHP() == 50);
		assertTrue(testpet.getHP() > 50);
	}
	
	
	
}

package edu.iris.dmc.station.conditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.iris.dmc.DocumentMarshaller;
import edu.iris.dmc.fdsn.station.model.FDSNStationXML;
import edu.iris.dmc.fdsn.station.model.Network;
import edu.iris.dmc.fdsn.station.model.Station;
import edu.iris.dmc.station.RuleEngineServiceTest;
import edu.iris.dmc.station.rules.Message;
import edu.iris.dmc.station.rules.NestedMessage;

public class Condition211Test {

	private FDSNStationXML theDocument;

	@BeforeEach
	public void init() throws Exception {

	}

	@Test
	public void fail() throws Exception {
		try (InputStream is = RuleEngineServiceTest.class.getClassLoader().getResourceAsStream("F1_211.xml")) {
			theDocument = DocumentMarshaller.unmarshal(is);

			Network n = theDocument.getNetwork().get(0);
			Station s = n.getStations().get(0);

			EpochOverlapCondition condition = new EpochOverlapCondition(true, "");
			Message result = condition.evaluate(s);
			System.out.println(result);
			NestedMessage nestedMessage=(NestedMessage)result;
			System.out.println(nestedMessage.getNestedMessages().get(0).getDescription());
			assertTrue(nestedMessage.getNestedMessages().get(0).getDescription().contains("Chan: BDF Loc: 00 2016-06-06T00:00:00 2019-09-30T23:59:59 epoch"));
		}

	}

	@Test
	public void pass() throws Exception {
		try (InputStream is = RuleEngineServiceTest.class.getClassLoader().getResourceAsStream("pass.xml")) {
			theDocument = DocumentMarshaller.unmarshal(is);

			Network n = theDocument.getNetwork().get(0);
			Station s = n.getStations().get(0);

			EpochOverlapCondition condition = new EpochOverlapCondition(true, "");

			Message result = condition.evaluate(s);
			assertTrue(result instanceof edu.iris.dmc.station.rules.Success);
		}

	}
}

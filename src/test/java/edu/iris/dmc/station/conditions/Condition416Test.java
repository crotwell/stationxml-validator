package edu.iris.dmc.station.conditions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.iris.dmc.DocumentMarshaller;
import edu.iris.dmc.fdsn.station.model.Channel;
import edu.iris.dmc.fdsn.station.model.FDSNStationXML;
import edu.iris.dmc.fdsn.station.model.Network;
import edu.iris.dmc.fdsn.station.model.Station;
import edu.iris.dmc.station.RuleEngineServiceTest;
import edu.iris.dmc.station.restrictions.ChannelCodeRestriction;
import edu.iris.dmc.station.restrictions.ChannelTypeRestriction;
import edu.iris.dmc.station.restrictions.Restriction;
import edu.iris.dmc.station.rules.Message;

public class Condition416Test {

	private FDSNStationXML theDocument;

	@BeforeEach
	public void init() throws Exception {

	}

	@Test
	public void fail() throws Exception {
		try (InputStream is = RuleEngineServiceTest.class.getClassLoader().getResourceAsStream("F1_416.xml")) {
			theDocument = DocumentMarshaller.unmarshal(is);

			Network n = theDocument.getNetwork().get(0);
			Station s = n.getStations().get(0);
			Channel c = s.getChannels().get(0);
			
			Restriction[] restrictions = new Restriction[] { new ChannelCodeRestriction(), new ChannelTypeRestriction() };

			InstrumentSensitivityCondition condition = new InstrumentSensitivityCondition(true, "", restrictions);
               
			Message result = condition.evaluate(c);
			
			assertTrue(result instanceof edu.iris.dmc.station.rules.Error);
		}

	}
	
	@Test
	public void passpoly() throws Exception {
		try (InputStream is = RuleEngineServiceTest.class.getClassLoader().getResourceAsStream("P1_415.xml")) {
			theDocument = DocumentMarshaller.unmarshal(is);

			Network n = theDocument.getNetwork().get(0);
			Station s = n.getStations().get(0);
			Channel c = s.getChannels().get(0);
			
			Restriction[] restrictions = new Restriction[] { new ChannelCodeRestriction(), new ChannelTypeRestriction() };

			InstrumentSensitivityCondition condition = new InstrumentSensitivityCondition(true, "", restrictions);
               
			Message result = condition.evaluate(c);
			
			assertTrue(result instanceof edu.iris.dmc.station.rules.Success);
		}

	}
	
	@Test
	public void passpoly2() throws Exception {
		try (InputStream is = RuleEngineServiceTest.class.getClassLoader().getResourceAsStream("P3_415.xml")) {
			theDocument = DocumentMarshaller.unmarshal(is);

			Network n = theDocument.getNetwork().get(0);
			Station s = n.getStations().get(0);
			Channel c = s.getChannels().get(0);
			
			Restriction[] restrictions = new Restriction[] { new ChannelCodeRestriction(), new ChannelTypeRestriction() };

			InstrumentSensitivityCondition condition = new InstrumentSensitivityCondition(true, "", restrictions);
               
			Message result = condition.evaluate(c);
			
			assertTrue(result instanceof edu.iris.dmc.station.rules.Success);
		}

	}

	@Test
	public void pass() throws Exception {
		try (InputStream is = RuleEngineServiceTest.class.getClassLoader().getResourceAsStream("pass.xml")) {
			theDocument = DocumentMarshaller.unmarshal(is);

			Network n = theDocument.getNetwork().get(0);
			Station s = n.getStations().get(0);
			Channel c = s.getChannels().get(0);
			InstrumentSensitivityCondition condition = new InstrumentSensitivityCondition(true, "");

			Message result = condition.evaluate(c);
			assertTrue(result instanceof edu.iris.dmc.station.rules.Success);
		}

	}
}

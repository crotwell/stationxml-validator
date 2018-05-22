package edu.iris.dmc.station.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import edu.iris.dmc.station.XmlUtil;
import edu.iris.dmc.station.rules.Message;
import edu.iris.dmc.station.rules.Result;

public class CsvPrintStream extends PrintStream implements RuleResultPrintStream {

	private static final Object[] FILE_HEADER = { "Source", "RuleId", "Network", "Station", "Channel", "Location",
			"StartDate", "EndDate", "Message" };
	private CSVPrinter csvFilePrinter = null;

	public CsvPrintStream(OutputStream out) throws IOException {
		super(out);
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(System.lineSeparator());
		this.csvFilePrinter = new CSVPrinter(this, csvFileFormat);
	}

	public void printHeader() throws IOException {
		csvFilePrinter.printRecord(FILE_HEADER);
	}

	public void print(Message message) throws IOException {
		print("", message);
	}

	public void print(String source, Message message) throws IOException {
		List<String> record = new ArrayList<>();

		record.add(source);
		record.add("" + message.getRule().getId());
		record.add(message.getNetwork() == null ? null : message.getNetwork().getCode());
		record.add(message.getStation() == null ? null : message.getStation().getCode());
		record.add(message.getChannel() == null ? null : message.getChannel().getCode());
		record.add(message.getChannel() == null ? null : message.getChannel().getLocationCode());

		if (message.getChannel() != null) {
			record.add(message.getChannel() == null ? null : XmlUtil.toText(message.getChannel().getStartDate()));
			record.add(message.getChannel() == null ? null : XmlUtil.toText(message.getChannel().getEndDate()));
		} else if (message.getStation() != null) {
			record.add(XmlUtil.toText(message.getStation().getStartDate()));
			record.add(XmlUtil.toText(message.getStation().getEndDate()));
		} else if (message.getNetwork() != null) {
			record.add(XmlUtil.toText(message.getNetwork().getStartDate()));
			record.add(XmlUtil.toText(message.getNetwork().getEndDate()));
		}

		record.add(message.getDescription());
		csvFilePrinter.printRecord(record);
		csvFilePrinter.flush();

	}

	@Override
	public void printFooter() {

	}

	@Override
	public void printRow(String text) {
		this.println(text);
	}

	@Override
	public void printMessage(String text) {
		this.print(text);
	}

}
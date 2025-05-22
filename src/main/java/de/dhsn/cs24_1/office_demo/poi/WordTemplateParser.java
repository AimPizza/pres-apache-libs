package de.dhsn.cs24_1.office_demo.poi;

import static de.dhsn.cs24_1.office_demo.shared.Utilities.log;
import static de.dhsn.cs24_1.office_demo.shared.Utilities.logError;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import de.dhsn.cs24_1.office_demo.shared.ReportModel;

/* Here we try to parse the Word template specified in @see de.dhsn.cs24_1.office_demo.WordTemplate.poi
 * 
 */
public class WordTemplateParser {

	public static ReportModel parseWordDocument(String wordFilePath) throws IOException {
		List<XWPFParagraph> paragraphs;
		ReportModel model = new ReportModel(null, null, null, null);
		Date date = new Date(); // NOTE: will initialize to now, be aware of that
		ArrayList<String> participants = new ArrayList<String>();
		ArrayList<String> agenda = new ArrayList<String>();
		ArrayList<String> notes = new ArrayList<String>();

		log("parsing meeting..");

		Path msWordPath = Paths.get(wordFilePath);

		XWPFDocument document = new XWPFDocument(Files.newInputStream(msWordPath));
		document = new XWPFDocument(Files.newInputStream(msWordPath));

		paragraphs = document.getParagraphs();
		document.close();

		String currentHeading = "";
		String pText = ""; // as in paragraphText

		for (XWPFParagraph paragraph : paragraphs) {

			pText = paragraph.getText().trim(); // I hope trimming the string won't pose a problem lol
			if (pText.isEmpty())
				continue;

			// handle headings
			if (paragraph.getStyle() != null && paragraph.getStyle().startsWith("Heading")) {

				switch (pText) {
				case WordTemplate.documentHeading:
					currentHeading = WordTemplate.documentHeading;
					break;
				case WordTemplate.attendeesHeading:
					currentHeading = WordTemplate.attendeesHeading;
					break;
				case WordTemplate.agendaHeading:
					currentHeading = WordTemplate.agendaHeading;
					break;
				case WordTemplate.notesHeading:
					currentHeading = WordTemplate.notesHeading;
					break;
				default:
					currentHeading = "";
					break;
				}

				continue;

			}

			if (currentHeading.length() > 0) {
				switch (currentHeading) {
				// we're very strict about the date format. It has to in the right format
				case WordTemplate.documentHeading:
					try {
						date = ReportModel.dateFormat.parse(pText);
					} catch (ParseException e) {
						logError("could not parse date");
					}
					break;
				// attendees must be separated by a comma
				case WordTemplate.attendeesHeading:
					String[] participantsArray = pText.split(",");
					for (String participant : participantsArray) {
						participants.add(participant.trim());
					}
					break;
				// all points of the agenda must be bullet points
				case WordTemplate.agendaHeading:
					if (paragraph.getNumID() != null) {
						agenda.add(pText);
					} else {
						log("disregarding agenda paragraph: " + pText);
					}
					break;
				// notes must be bullet points
				case WordTemplate.notesHeading:
					if (paragraph.getNumID() != null) {
						notes.add(pText);
					} else {
						log("disregarding note paragraph that is not a bullet point: " + pText);
					}
					break;
				default:
					break;
				}

			}

		}

		model.setDate(date);
		model.setParticipants(participants);
		model.setAgenda(agenda);
		model.setNotes(notes);
		log(model.toString());
		return model;
	}

	// TODO: should we handle this IOException?
	public static void main(String[] args) throws IOException {
		parseWordDocument(WordTemplate.output);
	}
}
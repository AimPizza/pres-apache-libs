package de.dhsn.cs24_1.office_demo.shared;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportModel {
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ArrayList<String> getParticipants() {
		return participants;
	}

	public void setParticipants(ArrayList<String> participants) {
		this.participants = participants;
	}

	public ArrayList<String> getAgenda() {
		return agenda;
	}

	public void setAgenda(ArrayList<String> agenda) {
		this.agenda = agenda;
	}

	public ArrayList<Note> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<Note> notes) {
		this.notes = notes;
	}

	public ReportModel(Date date, ArrayList<String> participants, ArrayList<String> agenda, ArrayList<Note> notes) {
		this.date = date;
		this.participants = participants;
		this.agenda = agenda;
		this.notes = notes;
	}

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	public Date date;
	public ArrayList<String> participants;
	public ArrayList<String> agenda;
	public ArrayList<Note> notes;

	public static class Note {
		public NoteType type;
		public String content;

		public Note(NoteType type, String content) {
			this.type = type;
			this.content = content;
		}
	}

	// Do not write a bulletpoint if the NoteType is PARAGRAPH
	public enum NoteType {
		PARAGRAPH, BULLET
	}

	// provided by chatgpt B)
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ReportModel{");
		sb.append("date=").append(dateFormat.format(date)).append(", participants=").append(participants)
				.append(", agenda=").append(agenda).append(", notes=[");

		// Notes: Bullet mit "- ", Paragraph ohne Präfix
		for (int i = 0; i < notes.size(); i++) {
			Note n = notes.get(i);
			if (n.type == NoteType.BULLET) {
				sb.append("- ");
			}
			sb.append(n.content);
			if (i < notes.size() - 1) {
				sb.append("; ");
			}
		}

		sb.append("]}");
		return sb.toString();
	}

}

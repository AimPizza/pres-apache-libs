package de.dhsn.cs24_1.office_demo.shared;

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
}

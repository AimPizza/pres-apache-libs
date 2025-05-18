package de.dhsn.cs24_1.office_demo.shared;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportModel {

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	public Date date;
	public ArrayList<String> participants;
	public ArrayList<String> agenda;
	public ArrayList<String> notes;

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

	public ArrayList<String> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	public ReportModel(Date date, ArrayList<String> participants, ArrayList<String> agenda, ArrayList<String> notes) {
		this.date = date;
		this.participants = participants;
		this.agenda = agenda;
		this.notes = notes;
	}

	// provided by ChatGPT B)
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ReportModel{");
		sb.append("date=").append(dateFormat.format(date)).append(", participants=").append(participants)
				.append(", agenda=").append(agenda).append(", notes=[");

		for (int i = 0; i < notes.size(); i++) {
			String n = notes.get(i);
			sb.append("- ");
			sb.append(n);
			if (i < notes.size() - 1) {
				sb.append("; ");
			}
		}

		sb.append("]}");
		return sb.toString();
	}

}

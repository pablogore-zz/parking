package com.spothero.gateway;

import javax.swing.text.StringContent;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by sg on 16/03/18.
 */
@XmlRootElement
public class Todo {
  private String summary;
  private String description;

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

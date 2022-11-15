package com.example.cultivate_chat_app.ui.weather;

import java.io.Serializable;

public class Weather {

   private final String mDayOfTheWeek;
   private final String mTemperature;
   private final String mConditions;

   public static class Builder implements Serializable {
      private final String mDayOfTheWeek;
      private final String mTemperature;
      private final String mConditions;

      public Builder(String dayOfTheWeek, String temperature, String conditions) {
         this.mDayOfTheWeek = dayOfTheWeek;
         this.mTemperature = temperature;
         this.mConditions = conditions;
      }
   }

   private Weather(final Builder builder) {
      this.mDayOfTheWeek = builder.mDayOfTheWeek;
      this.mTemperature = builder.mTemperature;
      this.mConditions = builder.mConditions;
   }

   public String getDayOfTheWeek() {
      return mDayOfTheWeek;
   }

   public String getTemperature() {
      return mTemperature;
   }

   public String getConditions() {
      return mConditions;
   }
}

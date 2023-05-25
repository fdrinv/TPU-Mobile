syntax = "proto3";

option java_package = "com.fedorinov.tpumobile";
option java_multiple_files = true;

message UserPreferences {
  string user_token = 1;
  
  string email = 2;
  string first_name = 3;
  string last_name = 4;
  string phone_number = 5;
  string group_name = 6;
  string language_id = 7;
  string language_name = 8;
  
}
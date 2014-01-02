require 'sinatra'

get "/" do
   erb :index, :locals => { :zipzop => "[TRANSLATION]" } 
end

post "/goodbye" do
   retval = "TRANSLATION CODE GOES HERE"
   erb :index, :locals => { :zipzop => retval }
end

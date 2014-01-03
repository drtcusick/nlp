package com.asynchrony.nlp.classifier;

public class TrainingSet {
	
	public static final String UNKNOWN = "Unknown";
	public static final String TRAINING_CATEGORIES[][] = 
		{{"-1", UNKNOWN}, {"0", UNKNOWN}, 
		{"1", "Thinking Strategically"}, {"2", "Determination"}, 
		{"3", "Composed"}, {"4", "Visualization"}, {"5", "Problems Perceiving Them"}};
	
	
	public static final String TRAINING_SET_PHRASES[][] = 
		{{"4", "Dan explained a user-driven approach to visualization and modeling."},
		{"4", "When visualizing the ideal plan, John was right there."},
		{"4", "Wes was eager to demonstrate incorporating visualization into a successful routine."},
		{"4", "Visualization of the required work was daunting until Ryan stepped to the podium."},
		{"4", "Edward wisely used to time to teach the team techniques in visualization."},
		{"4", "Greg is able to visualize the ideal of everything that he would like to achieve."},
		{"4", "Andi is able to visualize and analyze both basic and creative solutions."},
		{"4", "Visualization and planning explain the success of this project."},
		{"4", "Wilbert showed the team how visualization can help bring about remarkable change."},
		{"4", "James does all the right things; he visualizes and expresses gratitude."},
		{"2", "Thanks to Dave, the draft determination will protect customers from significant price shocks."},
		{"2", "In understanding the clients issues, Ben's determination never wavered."},
		{"2", "The overriding reason we landed the contract was Neil's determination to please the client."},
		{"2", "Next time you are tired or second guessing yourself, try to channel your determination."},
		{"2", "The determination in John is incredible, he never gives up."},
		{"2", "He has the ability to see past the challenge with his determination."},
		{"2", "Nathan returned to school with a determination to finish."},
		{"2", "As usual, Lisa approached the task with determination and energy."},
		{"2", "William's determination carried him through the battle."},
		{"2", "We learned quickly that the most important predictor of success is determination."},
		{"2", "Bob's determination would not allow him to leave the office until the task was completed."},
		{"2", "Through perseverance and determination, Jason ensured the project would be a success."},
		{"2", "Joe displayed a fierce determination not to accept defeat."},
		{"1", "Lee was thinking strategically when he organized our new digital Kanban board."},
		{"1", "During our retrospective, the entire team was thinking strategically in moving our standups to the phone system."},
		{"1", "The team lead, thinking strategically, brought healthy breakfast selections to our early morning meeting."},
		{"1", "By keeping our technology options open, Travis was thinking strategically."},
		{"1", "By bringing her lunch to work, Mackenzie was thinking strategically about organizing her day."},
		{"1", "Thinking strategically, Nate exchanged team developers on an individual basis."},
		{"1", "Ryan was thinking strategically when he took the initiative to learn a new technology."},
		{"1", "The team created a branching policy by thinking strategically."},
		{"1", "Steve was thinking strategically by suggesting the use of natural language processing."},
		{"1", "The dog was thinking strategically when he buried his extra bone in the back yard."},
		{"1", "Robert's decision to include documentation showed that he was thinking strategically."},
		{"1", "Jason's role is to ensure that the team is thinking strategically."},
		{"1", "The fate of the project was announced, showing he was thinking strategically."},
		{"1", "The presentation included strategically positioned highlights."},
		{"3", "Lee remained composed as the remote developers became upset."},
		{"3", "Sara maintained her composure as the client criticized her presentation."},
		{"3", "Although the room became heated, Bob remain completely composed."},
		{"3", "Composure was maintained by all as the bad news was delivered."},
		{"3", "June retained her composure after the non politically correct joke."},
		{"3", "Unsurprisingly, Sherri did not maintain her composure."},
		{"3", "As the bad news was delivered, everyone except Missi maintained their composure."},
		{"3", "Mark was as composed as a church mouse when he lost the game."},
		{"3", "I was surprised that Sarah did not remain composed after she was fired."},
		{"3", "Remaining composed was the focus of the motivational speaker."},
		{"3", "He accepted their problems with composure and she with equanimity."}, 
		{"5", "quickly saw the problem with the project"},
		{"5", "did a good job at determining that the system was not performing correctly"},
		{"5", "perceived that there were problems that were not being addressed"},
		{"5", "was able to see that the machine was broken"},
		{"5", "saw that the dependencies were not aligned correctly"},
		{"5", "became aware of the problems that were plaguing the installation"},
		{"5", "realized that the inefficiency was due to the machine design"},
		{"5", "discerned that the team was failing to perform well enough"},
		{"5", "identified that there was no responsible party identified"},
		{"5", "comprehended that the nature of the problem was machine based"},
		{"5", "understood that there were too many steps in the process"}
		};

}

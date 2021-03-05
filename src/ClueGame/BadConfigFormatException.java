package clueGame;

public class BadConfigFormatException extends Exception {
	private String message = "Bad file configuration format.";
	public BadConfigFormatException() {
		System.out.println("Bad file configuration format.");
	}
	public BadConfigFormatException(String message)
	{
		this.message = message;
		System.out.println(message);
	}
}

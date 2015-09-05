//Interface used for calling special commands

interface Command {
	public void performCommand(int commNum, byte[] parameter);
}

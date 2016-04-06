package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Special class extending ObjectOutputStream to enable the possibility of
 * saving to the same file using different ObjectOutputStreams without throwing
 * CorruptedStreamExceptions while trying to read that file.
 *
 * @author Antek
 *
 */
public class AppendingObjectOutputStream extends ObjectOutputStream
{

	public AppendingObjectOutputStream(OutputStream out) throws IOException
	{

		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException
	{
		reset();
	}

}

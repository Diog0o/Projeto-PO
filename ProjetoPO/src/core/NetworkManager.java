package core;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NetworkManager {
	
	private String _filename = "";
	
	  public void save() throws IOException, FileNotFoundException,
      MissingFileAssociationException {
		  if (_filename == null || _filename.isBlank())
			  throw new MissingFileAssociationException();

		  if (_warehouse.isDirty()) {
			  try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_filename)))) {
				  out.writeObject(_warehouse);
			  }
			  _warehouse.clean();
		  }
	  }
}



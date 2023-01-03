package controller.gestioneUtenza;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import model.Utente;
import model.UtenteDAO;

public class GestioneUtenzaServiceImpl implements GestioneUtenzaService{

	/**
     * @exclude
     **/
    private UtenteDAO utenteDao = new UtenteDAO();
    
    /**
     * Empty Constructor.
     **/
    public GestioneUtenzaServiceImpl() {
    }

    /**
     * Registers a new user account.
     * @param utente user into the db.
     * @return true if the operation ends in success.
     * @throws IOException
     * @throws IllegalArgumentException
     */
    @Override
    public boolean registerAccount(Utente utente)
            throws IllegalArgumentException {

        try {
			if(!searchAccountByEmail(utente.getMail())) {
				utenteDao.saveUtente(utente);
				return true;
			}else {
				throw new IllegalArgumentException("email gi� presente"
			            + " nel DB, utilizza una nuova email");
			}
		} catch (InterruptedException | ExecutionException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return false;
    }
    
    /**
     * Registers a new user account.
     * @param email is the email of the user to remove from the db.
     */
    @Override
	public boolean removeUtente(String email) {
		
    	return utenteDao.deleteUtente(email);
		
	}

	 /**
     * Checks if the credential entered are correct.
     * @param email of the user
     * @param password of the user
     * @return true if the user credentials correspond.
	 * @throws IOException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
     */
	@Override
	public boolean checkCredentials(String email, String password) throws InterruptedException, ExecutionException, IOException {

		Utente utente = getAccountByEmail(email);
		
		if(utente == null)
			return false;
		
		return utente.getPassword().equals(password);

	}

	/**
     * Checks if an account exists in the database.
     * @param email referring to the account to search for
     * @return true if the account exists, false if not
     */
    @Override
    public boolean searchAccountByEmail(String email)
            throws InterruptedException, ExecutionException, IOException {

        Utente utente = new Utente();
        utente = utenteDao.findUtenteByMail(email);
        
        if(utente == null)
        	return false;
        
        return true;
    }

    /**
     * This method return the account given its mail.
     * @param email referring to the account to search for
     * @return Utente if it exists, null if not
     */
	@Override
	public Utente getAccountByEmail(String email) throws InterruptedException, ExecutionException, IOException {
		
		Utente utente = new Utente();
		
        utente = utenteDao.findUtenteByMail(email);
                
        return utente == null ? null : utente;
	}

	/**
	 * Checks if the user is an Admin.
     * @param utente is the registred user into the DataBase.
     * @return true if the following account is
     * a Comun-ity Admin.
     */
	@Override
	public boolean IsAdmin(Utente utente) {
		
		return utente.getRuolo().equals("admin");
	}
	
	/**
	 * Checks if the user is a Pro.
     * @param utente is the registred user into the DataBase.
     * @return true if the following account is
     * a certified pro.
     */
	@Override
	public boolean isPro(Utente utente) {
		
		return utente.getRuolo().equals("professionista");
	}

	/**
	 * Retrieve all the Admins' email from the db.
     * @return a List of all Admins' email.
     */
	@Override
	public List<String> getAllAdminsEmails() {
		
		List<Utente> lista = utenteDao.getAllAdmins();
	    Iterator<Utente> it = lista.iterator();
	    
	    List<String> emails = new ArrayList<>();
		
		while(it.hasNext()){
			emails.add(it.next().getMail());
		}
		
		return emails;
	}

	/**
	 * Retrieve all the Users from the db.
     * @return a List of Utente that contains all the users.
     */
	@Override
	public List<Utente> getAllUsers() {
		List<Utente> lista = utenteDao.getAllUsers();
		return lista;
	}
	
	@Override
	public boolean changePassword(String email, String password)
			throws IOException, ExecutionException, InterruptedException {
		// TODO Auto-generated method stub
		
		return utenteDao.changePassword(email, password);
	}
}

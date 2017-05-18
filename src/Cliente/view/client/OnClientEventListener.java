package Cliente.view.client;

import commun.Conta;
import commun.Emprestimo;
import commun.Movimento;

import java.util.ArrayList;

public interface OnClientEventListener {
	
	public void onSelectedAccount(String account);
	public void onNewUserChange(String newName);
	public void onAmountTransfer(String amount, String account);
	public ArrayList<String> onGetAccountBalance();
	public ArrayList<Movimento> onGetAccountMovements();
	public ArrayList<Emprestimo> onGetAccountLoans();
	public ArrayList<Movimento> onGetAllAccountsMovements();
	public ArrayList<Emprestimo> onGetAllAccountsLoans();
	public ArrayList<Conta> onGetAccounts();
	public Conta onGetCurrentAccount();
}

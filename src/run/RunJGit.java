package run;

import java.io.IOException;
import java.util.Scanner;
import org.eclipse.jgit.api.errors.GitAPIException;
import controller.GitControl;
public class RunJGit {

	private RunJGit() {
		super();
	}

	public static void main(String[] args) throws IOException, GitAPIException {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter Local Repository Path Ex: C:/Repository/la-lde-hh");
		String localPath = sc.nextLine();
		
		System.out.println("Enter Username: ");
		String username = sc.nextLine();
		System.out.println("Enter Password: ");
		String password = sc.nextLine();
		
		System.out.println("Enter Branch Name: ");
		String branch = sc.nextLine();
		
		System.out.println("Enter Commit Message");
		String commitMessage = sc.nextLine();
		
		String remotePath = "REMOTE PATH IS NEEDED TO CLONE REPOSITORY";
        GitControl gc = new GitControl(localPath, remotePath,username,password,branch);
        //Clone repository
        //Add files to repository
        gc.addToRepo();
        //Commit with a custom message
        gc.commitToRepo(commitMessage);
        //Push commits
        gc.pushToRepo();
       
	}

}

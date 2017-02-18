package run;

import java.io.IOException;

import org.eclipse.jgit.api.errors.GitAPIException;

import controller.GitControl;

public class RunJGit {

	public static void main(String[] args) throws IOException, GitAPIException {
		
		String localPath = "C:/JGITTEST/JGITTEST";
        String remotePath = "https://github.com/agarwalamit662/JGITTEST.git";
        GitControl gc = new GitControl(localPath, remotePath,"agarwalamit662","amit1234");
        //Clone repository
        //gc.cloneRepo();
        //Add files to repository
        gc.addToRepo();
        //Commit with a custom message
        gc.commitToRepo("My First Commit");
        //Push commits
        gc.pushToRepo();
        //Pull
       // gc.pullFromRepo();
		
	}

}

package controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author Amit Agarwal
 *
 */
public class GitControl {

    private String localPath, remotePath;
    private Repository localRepo;
    private Git git;
    private CredentialsProvider cp;
    private String username;
    private String password;
    private String branch;
    
    public GitControl(String localPath, String remotePath,String username,String password,String branch) throws IOException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
        this.localPath = localPath;
        this.remotePath = remotePath;
        this.username = username;
        this.password = password;
        this.localRepo = new FileRepository(localPath + "/.git");
        this.branch = branch;
        cp = new UsernamePasswordCredentialsProvider(this.username, this.password);
        git = new Git(localRepo);
        git.checkout().setName(null != this.branch && !this.branch.trim().isEmpty()?this.branch: "master" ).call();
    }

    public void cloneRepo() throws IOException, NoFilepatternException, GitAPIException {
        Git.cloneRepository()
                .setURI(remotePath)
                .setDirectory(new File(localPath))
                .call();
    }

    public void addToRepo() throws IOException, NoFilepatternException, GitAPIException {
        AddCommand add = git.add();
        add.addFilepattern(".").call();
    }

    public void commitToRepo(String message) throws IOException, NoHeadException,
            NoMessageException, ConcurrentRefUpdateException,
            JGitInternalException, WrongRepositoryStateException, GitAPIException {
        git.commit().setMessage(message).call();
    }

    public void pushToRepo() throws IOException, JGitInternalException,
            InvalidRemoteException, GitAPIException {
        PushCommand pc = git.push();
        pc.setCredentialsProvider(cp)
                .setForce(true)
                .setPushAll();
        try {
            Iterator<PushResult> it = pc.call().iterator();
            if (it.hasNext()) {
                System.out.println(it.next().toString());
            }
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        }
    }

    public void pullFromRepo() throws IOException, WrongRepositoryStateException,
            InvalidConfigurationException, DetachedHeadException,
            InvalidRemoteException, CanceledException, RefNotFoundException,
            NoHeadException, GitAPIException {
        git.pull().call();
    }

}

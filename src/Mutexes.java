import java.util.*;

public class Mutexes {

Object File = new Object();
Object userInput = new Object();
Object userOutput = new Object();
int FileUsed = 0;
int InputUsed = 0;
int OutputUsed = 0;
boolean flag1 = Os.flagp1;
boolean flag2 = Os.flagp2;
boolean flag3 = Os.flagp3;


Queue<Object> BlockedQueueFile = new LinkedList<>();
Queue<Object> BlockedQueueInput = new LinkedList<>();
Queue<Object> BlockedQueueOutput = new LinkedList<>();

    public void semWaitFile() throws InterruptedException{
        synchronized (File){
            while (FileUsed > 0) {
                File.wait();
            }
            FileUsed++;
        }

    }

    public void semSignalFile() throws InterruptedException{
        synchronized (File){
            FileUsed--;
            File.notify();
        }


    }
    public void semWaituserInput() throws InterruptedException{
        synchronized (userInput){
            while (InputUsed > 0) {
                userInput.wait();
            }
            InputUsed++;
        }


    }

    public void semSignaluserInput() throws InterruptedException{
        synchronized (userInput){
            InputUsed--;
            userInput.notify();
        }

    }

    public void semWaituserOutput() throws InterruptedException{
        synchronized (userOutput){

            while (OutputUsed > 0) {
                userOutput.wait();
            }
            OutputUsed++;
        }

    }

    public void semSignaluserOutput() throws InterruptedException{
        synchronized (userOutput){
            OutputUsed--;
            userOutput.notify();
        }


    }
    public void FileMutex(Process P){
        if(FileUsed>0) {
            BlockedQueueFile.add(P);
        }
        else if (BlockedQueueFile.size()>0){
            BlockedQueueFile.remove();
            try {
                semWaitFile();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (flag1==true) {
                try {
                    semSignalFile();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else {
            try {
                semWaitFile();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (flag1==true) {
                try {
                    semSignalFile();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public void InputMutex(Process P) {
        if (InputUsed > 0) {
            BlockedQueueInput.add(P);
        } else if (BlockedQueueInput.size() > 0) {
            BlockedQueueInput.remove();
            try {
                semWaitFile();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (flag2 == true) {
                try {
                    semSignalFile();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            try {
                semWaituserInput();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (flag2 == true) {
                try {
                    semSignalFile();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

        public void ScreenMutex(Process P){
            if (OutputUsed > 0) {
                BlockedQueueOutput.add(P);
            } else if (BlockedQueueOutput.size() > 0) {
                BlockedQueueOutput.remove();
                try {
                    semWaitFile();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (flag3 == true) {
                    try {
                        semSignalFile();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                try {
                    semWaituserOutput();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (flag3 == true) {
                    try {
                        semSignalFile();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


    }


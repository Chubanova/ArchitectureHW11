package com.chubanova.state;

import com.chubanova.command.Command;
import java.util.Queue;

public interface State {

   State handle(Queue<Command> commands);

}

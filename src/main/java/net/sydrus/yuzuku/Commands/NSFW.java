package net.sydrus.yuzuku.Commands;

import net.dv8tion.jda.core.entities.*;
import net.sydrus.yuzuku.Constructors.Command;
import net.sydrus.yuzuku.Managers.LevelType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Thiago on 23/04/2017.
 */

public class NSFW extends Command {

    @Override
    public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type,
                             Boolean isEdited, String[] args) {
        String[] gifs = {"http://i.imgur.com/mRMACB0.gif", "http://i.imgur.com/mfdD0oJ.gif", "http://i.imgur.com/nWvlFa6.gif", "http://i.imgur.com/91qv1cg.gif", "http://i.imgur.com/MVuwKEu.gif", "http://i.imgur.com/LLgfINN.gif", "http://i.imgur.com/0ixcknU.gif", "http://i.imgur.com/vm5Cp4U.gif", "http://i.imgur.com/7QA3qxR.gif", "http://i.imgur.com/6C6Sg7V.gif", "http://i.imgur.com/DBduqxc.gif", "http://i.imgur.com/DBduqxc.gif", "http://i.imgur.com/CIX4Fs0.gif", "http://i.imgur.com/O8HKiJ7.gif", "http://i.imgur.com/dJLgqLm.gif", "http://i.imgur.com/1u9YQUE.gif", "http://i.imgur.com/Vs02pjH.gif", "http://i.imgur.com/5MW2koZ.gifv", "http://i.imgur.com/oVci8gS.gif", "http://i.imgur.com/coMJw2h.gif", "http://i.imgur.com/IGvwjE5.gifv", "http://i.imgur.com/NVS4Tcn.gifv", "http://i.imgur.com/vxf9dXK.gif", "http://i.imgur.com/OvJe0F0.gif", "http://i.imgur.com/clRg0Jj.gif", "http://i.imgur.com/hh16wgz.gif", "http://i.imgur.com/y1ydCST.gif", "http://i.imgur.com/Dxbggcb.gif", "http://i.imgur.com/4Lblaoj.gif", "http://i.imgur.com/i7DtjER.gif"};
        List<String> givenList = Arrays.asList(gifs);
        Random rand = new Random();
        String randomElement = givenList.get(rand.nextInt(givenList.size()));
        Chat.sendMessage(randomElement).queue();
        return true;
    }
}
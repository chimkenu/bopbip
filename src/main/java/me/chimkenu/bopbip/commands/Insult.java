package me.chimkenu.bopbip.commands;

import me.chimkenu.bopbip.Bopbip;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Insult implements CommandExecutor {

    private final String[] insults = {
            "I'd say your skills are cancer, but cancer actually kills people.",
            "Your family tree is a circle.",
            "Your as useless as the 'ueue' in 'queue'.",
            "Wait you've got something on your chin... No, the 3rd one down.",
            "You look like the one percent of bacteria that the hand sanitizer won't kill.",
            "You're 10 pounds of shit in a 5 pound bag.",
            "If my kids ever ask me what brain cancer is like, I'd start talking about you.",
            "I feel bad for whichever tree's oxygen you've wasted.",
            "You're the reason the gene pool needs a lifeguard.",
            "I'd call you gay, but I respect gay people.",
            "Someday you will go far... And I hope you stay there.",
            "Is your ass of jealous of the amount of shit that comes out of your mouth?",
            "I'm thinking of majoring in biotechnology so I can come up with a new birth control that identifies and kills any sperm that will one day be a mom's basement dwelling, oxygen draining, pizza stuffing, clod for the entirety of their life to prevent people like you to be brought into this world and exist on the same planet as me and my future children just to reproduce and create a chain of genetically inferior beings.",
            "You're proof that not all apes have evolved.",
            "I'd shove my foot up your ass if it wasn't already occupied by your own head.",
            "The only reason your parents kept you is because they knew you weren't worthy of adoption.",
            "I've barfed things smarter than you.",
            "You couldn't outsmart a houseplant.",
            "Your IQ is a proper fraction.",
            "You're so insignificant that even racism doesn't include you.",
            "Your parents didn't show up at your own birth.",
            "It's amazing how you brighten up a room when you leave.",
            "I bet your parents change the subject when people ask about you.",
            "I get why people talk about you when you're not around.",
            "You're not a mistake, you're a regret.",
            "Are you a professional moron or just a gifted amateur?",
            "I'd love to see things from your perspective but I'm afraid I can't shove my head that far up my ass.",
            "I'd agree with you, but then we would both be wrong.",
            "Expected nothing, still disappointed.",
            "Isn't it rather dangerous to use your entire vocabulary in a single sentence?",
            "You are proof of anal conception.",
            "I have no idea how we could be the same species with your IQ that low!",
            "Your brain is like a rock. No amount of facts or information can get through, but it's not like you can understand it anyway.",
            "Someone should just change the definition of idiot to /player/.",
            "Im sure even you would have a use in this world... Maybe fuel for a fire.",
            "God has left us and you're the punishment He has sent.",
            "Can you just die?",
            "Maybe I should just wait until your own stupidity kills you.",
            "I have 49 other insults just to show you how much I hate you!",
            "I've always wanted to know what its like being a fucking idiot... You must know, right?",
            "Your existence is a burden to everyone.",
            "If brains were gunpowder, you wouldn't have enough to blow your nose.",
            "I'd call you a cunt but you lack the warmth or depth.",
            "There are approximately 1,010,300 words in the English language, but I could never string enough words together to properly express how much I want to hit you with a chair.",
            "Don't you have to be stupid somewhere else?",
            "Every breath you take is a stunning endorsement of abortion.",
            "Im sure that an embryo has had a more meaningful life than yours.",
            "A post about the earth being flat has more sense than your entire being.",
            "Hi there! My name is Michael grover, and I am an explorer. Ever since I've been little, I've loved searching for new things. As a baby, my parents kept finding me in nooks and crannies around the house, 'on the search' as they would say. By the age of 5, I had been to every continent on the planet, barring Antarctica. For my 12th birthday, my parents got me diving lessons, by the time I was 13 I could scuba dive to a depth of 40 meters, as well as go cave diving. I got a pilots license at the age of 17, and learned to sail before my 18th birthday. Instead of going to university, I decided to travel around South America, exploring its rich jungles and beautiful landscapes. During my trip, I met my now wife, who was also an explorer. For our honeymoon, we sailed around the Caribbean, where we discovered 3 new islands, which we named after the cats I had growing up. Over the course of my life, I have come across great treasures and wondrous experience, but, in all my life, and all my travels, I'm afraid I have never come across a single person who asked for your opinion.",
            "You're the reason that Karen wants to talk to the manager.",
    };

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Provide an online player.");
                return true;
            }

            String message = insults[new Random().nextInt(insults.length)];

            // "Can you just die?" is a sequence of messages leading to the fake death of the player
            if (message.equals("Can you just die?")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {

                        // send the first message
                        target.sendMessage(ChatColor.RED + "Can you just die?");

                        new BukkitRunnable() {
                            @Override
                            public void run() {

                                // second message (death)
                                Bukkit.broadcastMessage(ChatColor.RESET + target.getDisplayName() + " died");

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        target.sendMessage(ChatColor.RED + "There we go.");
                                    }
                                }.runTaskLater(Bopbip.instance, 10);
                            }
                        }.runTaskLater(Bopbip.instance, 20);
                    }
                }.runTaskLater(Bopbip.instance, 1);
            }

            // This is for every other message
            else {
                message = message.replaceAll("/player/", target.getDisplayName());
                target.sendMessage(ChatColor.RED + message);
            }

            sender.sendMessage(Bopbip.prefix + ChatColor.YELLOW + "Sent the insult.");
        }

        else sender.sendMessage(ChatColor.RED + "Use the command properly. " + ChatColor.GRAY + "/insult <player>");
        return true;
    }
}

/*     */ package net.sydrus.yuzuku.Commands;
/*     */ 
/*     */ import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
/*     */ import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
/*     */ import java.awt.Color;
/*     */ import java.util.List;
/*     */ import net.dv8tion.jda.core.entities.Guild;
/*     */ import net.dv8tion.jda.core.entities.GuildVoiceState;
/*     */ import net.dv8tion.jda.core.entities.Member;
/*     */ import net.dv8tion.jda.core.entities.Message;
/*     */ import net.dv8tion.jda.core.entities.TextChannel;
/*     */ import net.dv8tion.jda.core.entities.User;
/*     */ import net.dv8tion.jda.core.entities.VoiceChannel;
/*     */ import net.dv8tion.jda.core.requests.RestAction;
/*     */ import net.sydrus.yuzuku.FileReader.ConfigReader;
/*     */ import net.sydrus.yuzuku.Managers.EmbedManager;
/*     */ import net.sydrus.yuzuku.Managers.GuildManager;
/*     */ import net.sydrus.yuzuku.Managers.HelpManager;
/*     */ import net.sydrus.yuzuku.Managers.LevelType;
/*     */ import net.sydrus.yuzuku.YuzukuBot;
/*     */ import net.sydrus.yuzuku.audio.AudioException;
/*     */ import net.sydrus.yuzuku.audio.AudioException.ExceptionType;
/*     */ import net.sydrus.yuzuku.audio.GuildMusicManager;
/*     */ import net.sydrus.yuzuku.audio.MusicManager;
/*     */ import net.sydrus.yuzuku.audio.TrackScheduler;
/*     */ import net.sydrus.yuzuku.exceptions.OutOfRangeException;
/*     */ 
/*     */ public class music extends net.sydrus.yuzuku.Constructors.Command
/*     */ {
/*  31 */   MusicManager manager = YuzukuBot.MusicManager;
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onCommand(User Sender, Message Message, Guild Guild, TextChannel Chat, List<LevelType> type, Boolean isEdited, String[] args)
/*     */   {
/*  37 */     if (!this.manager.isStarted(Guild)) {
/*  38 */       this.manager.startCode(Guild);
/*     */     }
/*  40 */     if ((YuzukuBot.guildManager.hasMusicInConfig(Guild)) && (this.manager.getGuildMusicManager(Guild).musicSize() == 0) && 
/*  41 */       (YuzukuBot.guildManager.playlistIsLoaded())) {
/*  42 */       Chat.sendMessage(embedMessage("Please wait, I'm loading the music", Color.YELLOW)).queue();
/*  43 */       return true;
/*     */     }
/*  45 */     if (args.length == 0) {
/*  46 */       lenght0Message(Chat, Guild);
/*  47 */     } else if ((args.length == 2) && (args[0].equalsIgnoreCase("add"))) {
/*  48 */       this.manager.addItems(Guild, Chat, args[1]);
/*  49 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("play"))) {
/*  50 */       if (this.manager.hasConnection(Guild)) {
/*  51 */         if (this.manager.hasMusic(Guild)) {
/*  52 */           if (this.manager.isPaused(Guild)) {
/*  53 */             this.manager.Pause(Guild, false);
/*     */           }
/*  55 */           else if (!this.manager.getGuildMusicManager(Guild).isPlaying()) {
/*  56 */             this.manager.Start(Guild, Chat);
/*  57 */             this.manager.setVolume(Guild, YuzukuBot.guildManager.getVolume(Guild));
/*  58 */             Chat.sendMessage(nowPlaying(Guild, true)).queue();
/*     */           } else {
/*  60 */             Chat.sendMessage(embedMessage("I'm already playing a music", Color.RED)).queue();
/*     */           }
/*     */         }
/*     */         else {
/*  64 */           Chat.sendMessage(embedMessage("**:x: No music found**", Color.RED)).queue();
/*     */         }
/*     */       } else {
/*  67 */         Chat.sendMessage(embedMessage("**:x: I'm not connected to a channel**", Color.RED)).queue();
/*     */       }
/*     */     }
/*  70 */     else if ((args.length == 2) && (args[0].equalsIgnoreCase("play"))) {
/*  71 */       if (this.manager.hasConnection(Guild)) {
/*  72 */         if (this.manager.hasMusic(Guild)) {
/*     */           try {
/*  74 */             int music_queue = Integer.valueOf(args[1]).intValue();
/*  75 */             if ((music_queue <= this.manager.getGuildMusicManager(Guild).musicSize()) && (music_queue > 0)) {
/*  76 */               if (this.manager.getGuildMusicManager(Guild).getAtualQueue() != music_queue) {
/*  77 */                 if (this.manager.getGuildMusicManager(Guild).isPlaying()) {
/*  78 */                   this.manager.Stop(Guild);
/*  79 */                   this.manager.closeConnection(Guild);
/*     */                 }
/*  81 */                 this.manager.Start(Guild, Chat, music_queue);
/*  82 */                 this.manager.setVolume(Guild, YuzukuBot.guildManager.getVolume(Guild));
/*  83 */                 Chat.sendMessage(nowPlaying(Guild, true)).queue();
/*     */               } else {
/*  85 */                 Chat.sendMessage(embedMessage("I'm already playing this song", Color.RED)).queue();
/*     */               }
/*     */             } else {
/*  88 */               Chat.sendMessage(embedMessage("Invalid music " + music_queue, Color.RED)).queue();
/*     */             }
/*     */           } catch (Exception e) {
/*  91 */             e.printStackTrace();
/*  92 */             Chat.sendMessage(embedMessage("Error: " + e.getMessage(), Color.RED)).queue();
/*     */           }
/*     */         } else {
/*  95 */           Chat.sendMessage(embedMessage("**:x: No music found**", Color.RED)).queue();
/*     */         }
/*     */       } else {
/*  98 */         Chat.sendMessage(embedMessage("**:x: I'm not connected on any channel**", Color.RED)).queue();
/*     */       }
/*     */     }
/* 101 */     else if ((args.length == 1) && (args[0].equalsIgnoreCase("pause"))) {
/* 102 */       if (this.manager.hasConnection(Guild)) {
/* 103 */         if (this.manager.getGuildMusicManager(Guild).isPlaying()) {
/* 104 */           this.manager.Pause(Guild, true);
/* 105 */           Chat.sendMessage(embedMessage("Paused", Color.YELLOW)).queue();
/*     */         }
/*     */       } else {
/* 108 */         Chat.sendMessage(embedMessage("**:x: I'm not connected on any channel**", Color.RED)).queue();
/*     */       }
/* 110 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("stop"))) {
/*     */       try {
/* 112 */         if (this.manager.hasConnection(Guild)) {
/* 113 */           if ((this.manager.getGuildMusicManager(Guild).isPlaying()) || 
/* 114 */             (this.manager.getGuildMusicManager(Guild).getPlayer().isPaused())) {
/* 115 */             this.manager.Stop(Guild);
/* 116 */             this.manager.closeConnection(Guild);
/* 117 */             Chat.sendMessage(embedMessage("** Stopped By (" + Sender.getName() + ")**")).queue();
/*     */           } else {
/* 119 */             Chat.sendMessage(embedMessage("I'm not playing or I'm paused", Color.RED)).queue();
/*     */           }
/*     */         } else {
/* 122 */           Chat.sendMessage(embedMessage("**:x: I'm not connected**", Color.RED)).queue();
/*     */         }
/*     */       } catch (Exception e) {
/* 125 */         Chat.sendMessage(embedMessage("**Error on stop**", Color.RED)).queue();
/*     */       }
/* 127 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("pn"))) {
/* 128 */       if (this.manager.getGuildMusicManager(Guild).isPlaying()) {
/*     */         try {
/* 130 */           Chat.sendMessage(nowPlaying(Guild, true)).queue();
/*     */         } catch (Exception e) {
/* 132 */           Chat.sendMessage(embedMessage("**Error on get what is playing now**", Color.RED)).queue();
/*     */         }
/*     */       } else {
/* 135 */         Chat.sendMessage(embedMessage("**I'm not playing anything now**", Color.RED)).queue();
/*     */       }
/* 137 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("join"))) {
/* 138 */       if (!this.manager.hasConnection(Guild)) {
/* 139 */         if (Guild.getMember(Sender).getVoiceState().inVoiceChannel()) {
/* 140 */           VoiceChannel voice = Guild.getMember(Sender).getVoiceState().getChannel();
/* 141 */           this.manager.joinVoiceChannel(Guild, voice);
/* 142 */           Chat.sendMessage(embedMessage("**I'm connected in to the channel :musical_note: (" + voice
/* 143 */             .getName() + ")** by (" + Sender.getName() + ")"))
/* 144 */             .queue();
/*     */         } else {
/* 146 */           Chat.sendMessage(embedMessage("**You need to be on a channel to do this**", Color.RED)).queue();
/*     */         }
/*     */       } else {
/* 149 */         Chat.sendMessage(embedMessage(":x: **I'm already on a channel**", Color.RED)).queue();
/*     */       }
/* 151 */     } else if ((args.length == 2) && (args[0].equalsIgnoreCase("join"))) {
/* 152 */       if (!this.manager.hasConnection(Guild)) {
/* 153 */         VoiceChannel voice = getVoiceChannel(Guild, args[1]);
/* 154 */         if (voice == null) {
/* 155 */           Chat.sendMessage("**" + args[1] + ":x: Channel does not exist**").queue();
/*     */         } else {
/* 157 */           this.manager.joinVoiceChannel(Guild, voice);
/* 158 */           Chat.sendMessage(embedMessage("**I'm connected in :musical_note: (" + voice.getName() + ") by (" + Sender
/* 159 */             .getName() + ")**")).queue();
/*     */         }
/*     */       } else {
/* 162 */         Chat.sendMessage(embedMessage(":x: **I'm already on a channel**", Color.RED)).queue();
/*     */       }
/* 164 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("leave"))) {
/* 165 */       if (this.manager.hasConnection(Guild)) {
/* 166 */         this.manager.Stop(Guild);
/* 167 */         this.manager.leaveChannel(Guild);
/* 168 */         Chat.sendMessage(embedMessage("**Disconnected**")).queue();
/*     */       } else {
/* 170 */         Chat.sendMessage(embedMessage(":x: I'm not connected on any channel", Color.RED)).queue();
/*     */       }
/* 172 */     } else if ((args.length == 2) && (args[0].equalsIgnoreCase("setv"))) {
/* 173 */       if (this.manager.hasConnection(Guild)) {
/* 174 */         int volume = Integer.valueOf(args[1]).intValue();
/* 175 */         if (volume <= 100) {
/* 176 */           this.manager.setVolume(Guild, volume);
/* 177 */           GuildManager gm = YuzukuBot.guildManager;
/* 178 */           gm.setVolume(Guild, volume);
/* 179 */           Chat.sendMessage(embedMessage("**Volume set to (" + volume + "%)**")).queue();
/*     */         } else {
/* 181 */           Chat.sendMessage(embedMessage("**The volume can not be greater than 100%**", Color.RED)).queue();
/*     */         }
/*     */       } else {
/* 184 */         Chat.sendMessage(embedMessage(":x: **I'm not connected on any channel**", Color.RED)).queue();
/*     */       }
/* 186 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("skip"))) {
/* 187 */       if (this.manager.getGuildMusicManager(Guild).isPlaying()) {
/*     */         try {
/* 189 */           if (this.manager.getGuildMusicManager(Guild).hasNextTrack()) {
/* 190 */             this.manager.SkipMusic(Guild);
/* 191 */             Chat.sendMessage(nowPlaying(Guild, true)).queue();
/*     */           } else {
/* 193 */             Chat.sendMessage(embedMessage("**There's no other music to play.**", Color.YELLOW));
/*     */           }
/*     */         } catch (AudioException e) {
/* 196 */           if (e.getExceptionType() == AudioException.ExceptionType.isFatal) {
/* 197 */             Chat.sendMessage(embedMessage(e.getMessage(), Color.RED)).queue();
/* 198 */           } else if (e.getExceptionType() == AudioException.ExceptionType.isFatal) {
/* 199 */             Chat.sendMessage(embedMessage(":x: **Error on skip music:** " + e.getMessage(), Color.RED)).queue();
/* 200 */           } else if (e.getExceptionType() == AudioException.ExceptionType.IsWarning) {
/* 201 */             Chat.sendMessage(embedMessage(e.getMessage(), Color.YELLOW)).queue();
/*     */           }
/*     */         } catch (IndexOutOfBoundsException e) {
/* 204 */           Chat.sendMessage(embedMessage(e.getMessage(), Color.YELLOW)).queue();
/*     */         }
/*     */       } else {
/* 207 */         Chat.sendMessage(embedMessage("**I'm not playing anything now!**", Color.RED)).queue();
/*     */       }
/* 209 */     } else if ((args.length == 1) && (args[0].equalsIgnoreCase("list"))) {
/* 210 */       GuildMusicManager musicman = this.manager.getGuildMusicManager(Guild);
/* 211 */       Chat.sendMessage(embedMessage("**Music list: " + musicman.musicSize() + "**")).queue();
/*     */       String toReturn;
/*     */       int position;
/* 214 */       if (this.manager.hasMusic(Guild)) {
/* 215 */         toReturn = "";
/* 216 */         position = 0;
/*     */         
/* 218 */         for (AudioTrack track : this.manager.getGuildMusicManager(Guild).scheduler.getTrucks()) {
/* 219 */           toReturn = toReturn + "(" + position + ") " + track.getInfo().title;
/* 220 */           position++;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 225 */         Chat.sendMessage(embedMessage("No musics")).queue();
/*     */       }
/* 227 */     } else if ((args.length != 1) || (!args[0].equalsIgnoreCase("shuffle")))
/*     */     {
/* 229 */       if ((args.length == 1) && (args[0].equalsIgnoreCase("asp"))) {
/* 230 */         GuildManager gm = YuzukuBot.guildManager;
/* 231 */         if (gm.autoSaveMusic(Guild)) {
/* 232 */           gm.setAutosave(Guild, false);
/* 233 */           Chat.sendMessage(embedMessage("**Auto save**  __**off**__")).queue();
/*     */         } else {
/* 235 */           gm.setAutosave(Guild, true);
/* 236 */           Chat.sendMessage(embedMessage("**Auto save** __**on**__")).queue();
/*     */         }
/* 238 */       } else if ((args.length == 1) && (args[0].equalsIgnoreCase("clear"))) {
/* 239 */         ConfigReader rdr = new ConfigReader("Guilds", Guild.getId());
/* 240 */         if (this.manager.hasMusic(Guild)) {
/* 241 */           this.manager.clearTracks(Guild);
/* 242 */           if (rdr.contains("PlayList")) {
/* 243 */             rdr.set("PlayList", null);
/* 244 */             rdr.save();
/* 245 */             rdr.reload();
/*     */           }
/* 247 */           this.manager.Stop(Guild);
/* 248 */           this.manager.closeConnection(Guild);
/* 249 */           Chat.sendMessage(embedMessage("**Songs removed**")).queue();
/*     */         } else {
/* 251 */           Chat.sendMessage(embedMessage("**No songs to remove**")).queue();
/*     */         }
/* 253 */       } else if ((args.length == 1) && (args[0].equalsIgnoreCase("reset"))) {
/*     */         try {
/* 255 */           this.manager.closeConnection(Guild);
/* 256 */           Chat.sendMessage(embedMessage("**I've tried resetting your connection, if this not work, contact the developer, It may be necessary to wait between 1 and 5 minutes**", Color.YELLOW))
/*     */           
/* 258 */             .queue();
/*     */         } catch (Exception e) {
/* 260 */           e.printStackTrace();
/* 261 */           Chat.sendMessage("__**Error**__ **trying to reset AudioPlayer. Send this error to the author of** " + YuzukuBot.botName + " :" + e)
/* 262 */             .queue();
/*     */         }
/* 264 */       } else if ((args.length == 2) && (args[0].equalsIgnoreCase("snm"))) {
/*     */         try {
/* 266 */           int music_queue = Integer.valueOf(args[1]).intValue();
/*     */           try {
/* 268 */             this.manager.setNextQueue(Guild, music_queue);
/* 269 */             EmbedManager embManager = new EmbedManager();
/* 270 */             AudioTrack track = this.manager.getSettedNextQueue(Guild);
/* 271 */             embManager.setColor(Color.GREEN);
/* 272 */             embManager.addField("Playing Now", track.getInfo().title, true);
/* 273 */             embManager.addField("Durations", track.getDuration() + "", true);
/* 274 */             embManager.addField("Position in queue", this.manager.getSettedNextQueueInt(Guild) + "/" + this.manager
/* 275 */               .getGuildMusicManager(Guild).musicSize(), true);
/* 276 */             Chat.sendMessage(embManager.getMessage()).queue();
/*     */           } catch (OutOfRangeException e) {
/* 278 */             Chat.sendMessage(embedMessage("Error: " + e.getMessage(), Color.RED)).queue();
/*     */           }
/*     */         } catch (Exception e) {
/* 281 */           Chat.sendMessage(embedMessage("Error: " + e.getMessage(), Color.RED)).queue();
/*     */         }
/* 283 */       } else if ((args.length == 1) && (args[0].equalsIgnoreCase("nm"))) {
/* 284 */         if (this.manager.getSettedNextQueueInt(Guild) > 0) {
/* 285 */           EmbedManager embManager = new EmbedManager();
/* 286 */           AudioTrack track = this.manager.getSettedNextQueue(Guild);
/* 287 */           embManager.setColor(Color.GREEN);
/* 288 */           embManager.addField("Playing Now", track.getInfo().title, true);
/* 289 */           embManager.addField("Durations", track.getDuration() + "", true);
/* 290 */           embManager.addField("Position in queue", this.manager
/* 291 */             .getSettedNextQueueInt(Guild) + "/" + this.manager.getGuildMusicManager(Guild).musicSize(), true);
/*     */           
/* 293 */           Chat.sendMessage(embManager.getMessage()).queue();
/*     */         } else {
/* 295 */           Chat.sendMessage(embedMessage("**No music was defined as next**")).queue();
/*     */         }
/*     */       } else {
/* 298 */         lenght0Message(Chat, Guild);
/*     */       } }
/* 300 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isConnected(Member member) {
/* 304 */     return member.getVoiceState().getChannel() != null;
/*     */   }
/*     */   
/*     */   private void lenght0Message(TextChannel channel, Guild guild) {
/* 308 */     HelpManager help = new HelpManager();
/* 309 */     help.setPrefix("{s}", YuzukuBot.guildManager.getPrefix(guild) + "music");
/* 310 */     help.addItem("\t{s}add <Playlist/YoutubeUrl> - ", "**Add songs**");
/* 311 */     help.addItem("\t{s}play - ", "**Play the selected song**");
/* 312 */     help.addItem("\t{s}pause - ", "**Pause the music**");
/* 313 */     help.addItem("\t{s}stop - ", "**Stop the music**");
/* 314 */     help.addItem("\t{s}skip - ", "**Skip the music**");
/* 315 */     help.addItem("\t{s}snm - ", "**Sets the next music**");
/* 316 */     help.addItem("\t{s}nm - ", "**See the next music**");
/* 317 */     help.addItem("\t{s}pn - ", "**See what's playing now**");
/* 318 */     help.addItem("\t{s}setv - ", "**Set the volume of the music**");
/* 319 */     help.addItem("\t{s}join <channel> - ", "**Enter the defined channel, if no channel is defined the bot will enter the channel you are**");
/*     */     
/* 321 */     help.addItem("\t{s}leave - ", "**Exits the voice channel if it is connected** ");
/* 322 */     help.addItem("\t{s}asp - ", "**For songs to be saved in the setting when adding. Use <True/False>**");
/* 323 */     help.addItem("\t{s}shuffle - ", "**Shuffle the songs**");
/* 324 */     help.addItem("\t{s}reset - ", "**Enter this command if your audio player is not responding / working so that it is reset**");
/*     */     
/* 326 */     help.addItem("\t{s}clear - ", "**Removes all songs, including songs from the settings, even if the auto save option is turned off**");
/*     */     
/*     */ 
/* 329 */     channel.sendMessage(help.getEmbedManager(net.sydrus.yuzuku.Managers.HelpManager.HelpType.UseComment).getMessage()).queue();
/*     */   }
/*     */   
/*     */   private VoiceChannel getVoiceChannel(Guild guild, String name)
/*     */   {
/* 334 */     for (VoiceChannel channel : guild.getVoiceChannels()) {
/* 335 */       if (channel.getName().equalsIgnoreCase(name)) {
/* 336 */         return channel;
/*     */       }
/*     */     }
/* 339 */     return null;
/*     */   }
/*     */   
/*     */   private Message nowPlaying(Guild guild, boolean getNextTrack) {
/* 343 */     GuildMusicManager musicman = this.manager.getGuildMusicManager(guild);
/* 344 */     AudioTrackInfo info = musicman.getPlayer().getPlayingTrack().getInfo();
/* 345 */     EmbedManager manager = new EmbedManager();
/* 346 */     manager.setColor(Color.GREEN);
/* 347 */     manager.addField("Playing Now", info.title, true);
/* 348 */     manager.addField("Durations", musicman.getPlayingTrack().getDuration() + "", true);
/* 349 */     manager.addField("Volume", this.manager.getVolume(guild) + "", true);
/* 350 */     if ((getNextTrack) && 
/* 351 */       (musicman.hasNextTrack())) {
/*     */       try {
/* 353 */         manager.addField("**Next Track**", musicman.getNextTrack().getInfo().title, false);
/*     */       } catch (Exception e) {
/* 355 */         e.printStackTrace();
/* 356 */         manager.addField("**Next Track**", "**Erro on give**", false);
/*     */       }
/*     */     }
/*     */     
/* 360 */     manager.addField("**Position in queue**", musicman.getAtualQueue() + "/" + musicman.musicSize(), true);
/* 361 */     if ((getNextTrack) && 
/* 362 */       (musicman.hasNextTrack())) {
/* 363 */       manager.addField("**Next position in queue**", musicman.nextQueue() + "/" + musicman.musicSize(), true);
/*     */     }
/*     */     
/* 366 */     return manager.getMessage();
/*     */   }
/*     */ }


/* Location:              C:\Users\Thiago\Desktop\Bot\YuzukuBot-2.3.7.jar!\net\sydrus\yuzuku\Commands\music.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
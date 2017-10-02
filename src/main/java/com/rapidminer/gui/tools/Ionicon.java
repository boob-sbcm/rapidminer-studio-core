/**
 * Copyright (C) 2001-2017 by RapidMiner and the contributors
 * 
 * Complete list of developers available at our web site:
 * 
 * http://rapidminer.com
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see http://www.gnu.org/licenses/.
*/
package com.rapidminer.gui.tools;

import com.rapidminer.tools.LogService;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;


/**
 * Enum for ionicons. The method {@link #getHtml()} supplies the html to include
 * the ionicon into an html label.
 *
 * @author Gisa Schaefer
 */
public enum Ionicon {
    /**
     * Alert ionicon.
     */
    ALERT('\uf101'),
    /**
     * Alert circled ionicon.
     */
    ALERT_CIRCLED('\uf100'),
    /**
     * Android add ionicon.
     */
    ANDROID_ADD('\uf2c7'),
    /**
     * Android add circle ionicon.
     */
    ANDROID_ADD_CIRCLE('\uf359'),
    /**
     * Android alarm clock ionicon.
     */
    ANDROID_ALARM_CLOCK('\uf35a'),
    /**
     * Android alert ionicon.
     */
    ANDROID_ALERT('\uf35b'),
    /**
     * Android apps ionicon.
     */
    ANDROID_APPS('\uf35c'),
    /**
     * Android archive ionicon.
     */
    ANDROID_ARCHIVE('\uf2c9'),
    /**
     * Android arrow back ionicon.
     */
    ANDROID_ARROW_BACK('\uf2ca'),
    /**
     * Android arrow down ionicon.
     */
    ANDROID_ARROW_DOWN('\uf35d'),
    /**
     * Android arrow dropdown ionicon.
     */
    ANDROID_ARROW_DROPDOWN('\uf35f'),
    /**
     * Android arrow dropdown circle ionicon.
     */
    ANDROID_ARROW_DROPDOWN_CIRCLE('\uf35e'),
    /**
     * Android arrow dropleft ionicon.
     */
    ANDROID_ARROW_DROPLEFT('\uf361'),
    /**
     * Android arrow dropleft circle ionicon.
     */
    ANDROID_ARROW_DROPLEFT_CIRCLE('\uf360'),
    /**
     * Android arrow dropright ionicon.
     */
    ANDROID_ARROW_DROPRIGHT('\uf363'),
    /**
     * Android arrow dropright circle ionicon.
     */
    ANDROID_ARROW_DROPRIGHT_CIRCLE('\uf362'),
    /**
     * Android arrow dropup ionicon.
     */
    ANDROID_ARROW_DROPUP('\uf365'),
    /**
     * Android arrow dropup circle ionicon.
     */
    ANDROID_ARROW_DROPUP_CIRCLE('\uf364'),
    /**
     * Android arrow forward ionicon.
     */
    ANDROID_ARROW_FORWARD('\uf30f'),
    /**
     * Android arrow up ionicon.
     */
    ANDROID_ARROW_UP('\uf366'),
    /**
     * Android attach ionicon.
     */
    ANDROID_ATTACH('\uf367'),
    /**
     * Android bar ionicon.
     */
    ANDROID_BAR('\uf368'),
    /**
     * Android bicycle ionicon.
     */
    ANDROID_BICYCLE('\uf369'),
    /**
     * Android boat ionicon.
     */
    ANDROID_BOAT('\uf36a'),
    /**
     * Android bookmark ionicon.
     */
    ANDROID_BOOKMARK('\uf36b'),
    /**
     * Android bulb ionicon.
     */
    ANDROID_BULB('\uf36c'),
    /**
     * Android bus ionicon.
     */
    ANDROID_BUS('\uf36d'),
    /**
     * Android calendar ionicon.
     */
    ANDROID_CALENDAR('\uf2d1'),
    /**
     * Android call ionicon.
     */
    ANDROID_CALL('\uf2d2'),
    /**
     * Android camera ionicon.
     */
    ANDROID_CAMERA('\uf2d3'),
    /**
     * Android cancel ionicon.
     */
    ANDROID_CANCEL('\uf36e'),
    /**
     * Android car ionicon.
     */
    ANDROID_CAR('\uf36f'),
    /**
     * Android cart ionicon.
     */
    ANDROID_CART('\uf370'),
    /**
     * Android chat ionicon.
     */
    ANDROID_CHAT('\uf2d4'),
    /**
     * Android checkbox ionicon.
     */
    ANDROID_CHECKBOX('\uf374'),
    /**
     * Android checkbox blank ionicon.
     */
    ANDROID_CHECKBOX_BLANK('\uf371'),
    /**
     * Android checkbox outline ionicon.
     */
    ANDROID_CHECKBOX_OUTLINE('\uf373'),
    /**
     * Android checkbox outline blank ionicon.
     */
    ANDROID_CHECKBOX_OUTLINE_BLANK('\uf372'),
    /**
     * Android checkmark circle ionicon.
     */
    ANDROID_CHECKMARK_CIRCLE('\uf375'),
    /**
     * Android clipboard ionicon.
     */
    ANDROID_CLIPBOARD('\uf376'),
    /**
     * Android close ionicon.
     */
    ANDROID_CLOSE('\uf2d7'),
    /**
     * Android cloud ionicon.
     */
    ANDROID_CLOUD('\uf37a'),
    /**
     * Android cloud circle ionicon.
     */
    ANDROID_CLOUD_CIRCLE('\uf377'),
    /**
     * Android cloud done ionicon.
     */
    ANDROID_CLOUD_DONE('\uf378'),
    /**
     * Android cloud outline ionicon.
     */
    ANDROID_CLOUD_OUTLINE('\uf379'),
    /**
     * Android color palette ionicon.
     */
    ANDROID_COLOR_PALETTE('\uf37b'),
    /**
     * Android compass ionicon.
     */
    ANDROID_COMPASS('\uf37c'),
    /**
     * Android contact ionicon.
     */
    ANDROID_CONTACT('\uf2d8'),
    /**
     * Android contacts ionicon.
     */
    ANDROID_CONTACTS('\uf2d9'),
    /**
     * Android contract ionicon.
     */
    ANDROID_CONTRACT('\uf37d'),
    /**
     * Android create ionicon.
     */
    ANDROID_CREATE('\uf37e'),
    /**
     * Android delete ionicon.
     */
    ANDROID_DELETE('\uf37f'),
    /**
     * Android desktop ionicon.
     */
    ANDROID_DESKTOP('\uf380'),
    /**
     * Android document ionicon.
     */
    ANDROID_DOCUMENT('\uf381'),
    /**
     * Android done ionicon.
     */
    ANDROID_DONE('\uf383'),
    /**
     * Android done all ionicon.
     */
    ANDROID_DONE_ALL('\uf382'),
    /**
     * Android download ionicon.
     */
    ANDROID_DOWNLOAD('\uf2dd'),
    /**
     * Android drafts ionicon.
     */
    ANDROID_DRAFTS('\uf384'),
    /**
     * Android exit ionicon.
     */
    ANDROID_EXIT('\uf385'),
    /**
     * Android expand ionicon.
     */
    ANDROID_EXPAND('\uf386'),
    /**
     * Android favorite ionicon.
     */
    ANDROID_FAVORITE('\uf388'),
    /**
     * Android favorite outline ionicon.
     */
    ANDROID_FAVORITE_OUTLINE('\uf387'),
    /**
     * Android film ionicon.
     */
    ANDROID_FILM('\uf389'),
    /**
     * Android folder ionicon.
     */
    ANDROID_FOLDER('\uf2e0'),
    /**
     * Android folder open ionicon.
     */
    ANDROID_FOLDER_OPEN('\uf38a'),
    /**
     * Android funnel ionicon.
     */
    ANDROID_FUNNEL('\uf38b'),
    /**
     * Android globe ionicon.
     */
    ANDROID_GLOBE('\uf38c'),
    /**
     * Android hand ionicon.
     */
    ANDROID_HAND('\uf2e3'),
    /**
     * Android hangout ionicon.
     */
    ANDROID_HANGOUT('\uf38d'),
    /**
     * Android happy ionicon.
     */
    ANDROID_HAPPY('\uf38e'),
    /**
     * Android home ionicon.
     */
    ANDROID_HOME('\uf38f'),
    /**
     * Android image ionicon.
     */
    ANDROID_IMAGE('\uf2e4'),
    /**
     * Android laptop ionicon.
     */
    ANDROID_LAPTOP('\uf390'),
    /**
     * Android list ionicon.
     */
    ANDROID_LIST('\uf391'),
    /**
     * Android locate ionicon.
     */
    ANDROID_LOCATE('\uf2e9'),
    /**
     * Android lock ionicon.
     */
    ANDROID_LOCK('\uf392'),
    /**
     * Android mail ionicon.
     */
    ANDROID_MAIL('\uf2eb'),
    /**
     * Android map ionicon.
     */
    ANDROID_MAP('\uf393'),
    /**
     * Android menu ionicon.
     */
    ANDROID_MENU('\uf394'),
    /**
     * Android microphone ionicon.
     */
    ANDROID_MICROPHONE('\uf2ec'),
    /**
     * Android microphone off ionicon.
     */
    ANDROID_MICROPHONE_OFF('\uf395'),
    /**
     * Android more horizontal ionicon.
     */
    ANDROID_MORE_HORIZONTAL('\uf396'),
    /**
     * Android more vertical ionicon.
     */
    ANDROID_MORE_VERTICAL('\uf397'),
    /**
     * Android navigate ionicon.
     */
    ANDROID_NAVIGATE('\uf398'),
    /**
     * Android notifications ionicon.
     */
    ANDROID_NOTIFICATIONS('\uf39b'),
    /**
     * Android notifications none ionicon.
     */
    ANDROID_NOTIFICATIONS_NONE('\uf399'),
    /**
     * Android notifications off ionicon.
     */
    ANDROID_NOTIFICATIONS_OFF('\uf39a'),
    /**
     * Android open ionicon.
     */
    ANDROID_OPEN('\uf39c'),
    /**
     * Android options ionicon.
     */
    ANDROID_OPTIONS('\uf39d'),
    /**
     * Android people ionicon.
     */
    ANDROID_PEOPLE('\uf39e'),
    /**
     * Android person ionicon.
     */
    ANDROID_PERSON('\uf3a0'),
    /**
     * Android person add ionicon.
     */
    ANDROID_PERSON_ADD('\uf39f'),
    /**
     * Android phone landscape ionicon.
     */
    ANDROID_PHONE_LANDSCAPE('\uf3a1'),
    /**
     * Android phone portrait ionicon.
     */
    ANDROID_PHONE_PORTRAIT('\uf3a2'),
    /**
     * Android pin ionicon.
     */
    ANDROID_PIN('\uf3a3'),
    /**
     * Android plane ionicon.
     */
    ANDROID_PLANE('\uf3a4'),
    /**
     * Android playstore ionicon.
     */
    ANDROID_PLAYSTORE('\uf2f0'),
    /**
     * Android print ionicon.
     */
    ANDROID_PRINT('\uf3a5'),
    /**
     * Android radio button off ionicon.
     */
    ANDROID_RADIO_BUTTON_OFF('\uf3a6'),
    /**
     * Android radio button on ionicon.
     */
    ANDROID_RADIO_BUTTON_ON('\uf3a7'),
    /**
     * Android refresh ionicon.
     */
    ANDROID_REFRESH('\uf3a8'),
    /**
     * Android remove ionicon.
     */
    ANDROID_REMOVE('\uf2f4'),
    /**
     * Android remove circle ionicon.
     */
    ANDROID_REMOVE_CIRCLE('\uf3a9'),
    /**
     * Android restaurant ionicon.
     */
    ANDROID_RESTAURANT('\uf3aa'),
    /**
     * Android sad ionicon.
     */
    ANDROID_SAD('\uf3ab'),
    /**
     * Android search ionicon.
     */
    ANDROID_SEARCH('\uf2f5'),
    /**
     * Android send ionicon.
     */
    ANDROID_SEND('\uf2f6'),
    /**
     * Android settings ionicon.
     */
    ANDROID_SETTINGS('\uf2f7'),
    /**
     * Android share ionicon.
     */
    ANDROID_SHARE('\uf2f8'),
    /**
     * Android share alt ionicon.
     */
    ANDROID_SHARE_ALT('\uf3ac'),
    /**
     * Android star ionicon.
     */
    ANDROID_STAR('\uf2fc'),
    /**
     * Android star half ionicon.
     */
    ANDROID_STAR_HALF('\uf3ad'),
    /**
     * Android star outline ionicon.
     */
    ANDROID_STAR_OUTLINE('\uf3ae'),
    /**
     * Android stopwatch ionicon.
     */
    ANDROID_STOPWATCH('\uf2fd'),
    /**
     * Android subway ionicon.
     */
    ANDROID_SUBWAY('\uf3af'),
    /**
     * Android sunny ionicon.
     */
    ANDROID_SUNNY('\uf3b0'),
    /**
     * Android sync ionicon.
     */
    ANDROID_SYNC('\uf3b1'),
    /**
     * Android textsms ionicon.
     */
    ANDROID_TEXTSMS('\uf3b2'),
    /**
     * Android time ionicon.
     */
    ANDROID_TIME('\uf3b3'),
    /**
     * Android train ionicon.
     */
    ANDROID_TRAIN('\uf3b4'),
    /**
     * Android unlock ionicon.
     */
    ANDROID_UNLOCK('\uf3b5'),
    /**
     * Android upload ionicon.
     */
    ANDROID_UPLOAD('\uf3b6'),
    /**
     * Android volume down ionicon.
     */
    ANDROID_VOLUME_DOWN('\uf3b7'),
    /**
     * Android volume mute ionicon.
     */
    ANDROID_VOLUME_MUTE('\uf3b8'),
    /**
     * Android volume off ionicon.
     */
    ANDROID_VOLUME_OFF('\uf3b9'),
    /**
     * Android volume up ionicon.
     */
    ANDROID_VOLUME_UP('\uf3ba'),
    /**
     * Android walk ionicon.
     */
    ANDROID_WALK('\uf3bb'),
    /**
     * Android warning ionicon.
     */
    ANDROID_WARNING('\uf3bc'),
    /**
     * Android watch ionicon.
     */
    ANDROID_WATCH('\uf3bd'),
    /**
     * Android wifi ionicon.
     */
    ANDROID_WIFI('\uf305'),
    /**
     * Aperture ionicon.
     */
    APERTURE('\uf313'),
    /**
     * Archive ionicon.
     */
    ARCHIVE('\uf102'),
    /**
     * Arrow down a ionicon.
     */
    ARROW_DOWN_A('\uf103'),
    /**
     * Arrow down b ionicon.
     */
    ARROW_DOWN_B('\uf104'),
    /**
     * Arrow down c ionicon.
     */
    ARROW_DOWN_C('\uf105'),
    /**
     * Arrow expand ionicon.
     */
    ARROW_EXPAND('\uf25e'),
    /**
     * Arrow graph down left ionicon.
     */
    ARROW_GRAPH_DOWN_LEFT('\uf25f'),
    /**
     * Arrow graph down right ionicon.
     */
    ARROW_GRAPH_DOWN_RIGHT('\uf260'),
    /**
     * Arrow graph up left ionicon.
     */
    ARROW_GRAPH_UP_LEFT('\uf261'),
    /**
     * Arrow graph up right ionicon.
     */
    ARROW_GRAPH_UP_RIGHT('\uf262'),
    /**
     * Arrow left a ionicon.
     */
    ARROW_LEFT_A('\uf106'),
    /**
     * Arrow left b ionicon.
     */
    ARROW_LEFT_B('\uf107'),
    /**
     * Arrow left c ionicon.
     */
    ARROW_LEFT_C('\uf108'),
    /**
     * Arrow move ionicon.
     */
    ARROW_MOVE('\uf263'),
    /**
     * Arrow resize ionicon.
     */
    ARROW_RESIZE('\uf264'),
    /**
     * Arrow return left ionicon.
     */
    ARROW_RETURN_LEFT('\uf265'),
    /**
     * Arrow return right ionicon.
     */
    ARROW_RETURN_RIGHT('\uf266'),
    /**
     * Arrow right a ionicon.
     */
    ARROW_RIGHT_A('\uf109'),
    /**
     * Arrow right b ionicon.
     */
    ARROW_RIGHT_B('\uf10a'),
    /**
     * Arrow right c ionicon.
     */
    ARROW_RIGHT_C('\uf10b'),
    /**
     * Arrow shrink ionicon.
     */
    ARROW_SHRINK('\uf267'),
    /**
     * Arrow swap ionicon.
     */
    ARROW_SWAP('\uf268'),
    /**
     * Arrow up a ionicon.
     */
    ARROW_UP_A('\uf10c'),
    /**
     * Arrow up b ionicon.
     */
    ARROW_UP_B('\uf10d'),
    /**
     * Arrow up c ionicon.
     */
    ARROW_UP_C('\uf10e'),
    /**
     * Asterisk ionicon.
     */
    ASTERISK('\uf314'),
    /**
     * At ionicon.
     */
    AT('\uf10f'),
    /**
     * Backspace ionicon.
     */
    BACKSPACE('\uf3bf'),
    /**
     * Backspace outline ionicon.
     */
    BACKSPACE_OUTLINE('\uf3be'),
    /**
     * Bag ionicon.
     */
    BAG('\uf110'),
    /**
     * Battery charging ionicon.
     */
    BATTERY_CHARGING('\uf111'),
    /**
     * Battery empty ionicon.
     */
    BATTERY_EMPTY('\uf112'),
    /**
     * Battery full ionicon.
     */
    BATTERY_FULL('\uf113'),
    /**
     * Battery half ionicon.
     */
    BATTERY_HALF('\uf114'),
    /**
     * Battery low ionicon.
     */
    BATTERY_LOW('\uf115'),
    /**
     * Beaker ionicon.
     */
    BEAKER('\uf269'),
    /**
     * Beer ionicon.
     */
    BEER('\uf26a'),
    /**
     * Bluetooth ionicon.
     */
    BLUETOOTH('\uf116'),
    /**
     * Bonfire ionicon.
     */
    BONFIRE('\uf315'),
    /**
     * Bookmark ionicon.
     */
    BOOKMARK('\uf26b'),
    /**
     * Bowtie ionicon.
     */
    BOWTIE('\uf3c0'),
    /**
     * Briefcase ionicon.
     */
    BRIEFCASE('\uf26c'),
    /**
     * Bug ionicon.
     */
    BUG('\uf2be'),
    /**
     * Calculator ionicon.
     */
    CALCULATOR('\uf26d'),
    /**
     * Calendar ionicon.
     */
    CALENDAR('\uf117'),
    /**
     * Camera ionicon.
     */
    CAMERA('\uf118'),
    /**
     * Card ionicon.
     */
    CARD('\uf119'),
    /**
     * Cash ionicon.
     */
    CASH('\uf316'),
    /**
     * Chatbox ionicon.
     */
    CHATBOX('\uf11b'),
    /**
     * Chatbox working ionicon.
     */
    CHATBOX_WORKING('\uf11a'),
    /**
     * Chatboxes ionicon.
     */
    CHATBOXES('\uf11c'),
    /**
     * Chatbubble ionicon.
     */
    CHATBUBBLE('\uf11e'),
    /**
     * Chatbubble working ionicon.
     */
    CHATBUBBLE_WORKING('\uf11d'),
    /**
     * Chatbubbles ionicon.
     */
    CHATBUBBLES('\uf11f'),
    /**
     * Checkmark ionicon.
     */
    CHECKMARK('\uf122'),
    /**
     * Checkmark circled ionicon.
     */
    CHECKMARK_CIRCLED('\uf120'),
    /**
     * Checkmark round ionicon.
     */
    CHECKMARK_ROUND('\uf121'),
    /**
     * Chevron down ionicon.
     */
    CHEVRON_DOWN('\uf123'),
    /**
     * Chevron left ionicon.
     */
    CHEVRON_LEFT('\uf124'),
    /**
     * Chevron right ionicon.
     */
    CHEVRON_RIGHT('\uf125'),
    /**
     * Chevron up ionicon.
     */
    CHEVRON_UP('\uf126'),
    /**
     * Clipboard ionicon.
     */
    CLIPBOARD('\uf127'),
    /**
     * Clock ionicon.
     */
    CLOCK('\uf26e'),
    /**
     * Close ionicon.
     */
    CLOSE('\uf12a'),
    /**
     * Close circled ionicon.
     */
    CLOSE_CIRCLED('\uf128'),
    /**
     * Close round ionicon.
     */
    CLOSE_ROUND('\uf129'),
    /**
     * Closed captioning ionicon.
     */
    CLOSED_CAPTIONING('\uf317'),
    /**
     * Cloud ionicon.
     */
    CLOUD('\uf12b'),
    /**
     * Code ionicon.
     */
    CODE('\uf271'),
    /**
     * Code download ionicon.
     */
    CODE_DOWNLOAD('\uf26f'),
    /**
     * Code working ionicon.
     */
    CODE_WORKING('\uf270'),
    /**
     * Coffee ionicon.
     */
    COFFEE('\uf272'),
    /**
     * Compass ionicon.
     */
    COMPASS('\uf273'),
    /**
     * Compose ionicon.
     */
    COMPOSE('\uf12c'),
    /**
     * Connection bars ionicon.
     */
    CONNECTION_BARS('\uf274'),
    /**
     * Contrast ionicon.
     */
    CONTRAST('\uf275'),
    /**
     * Crop ionicon.
     */
    CROP('\uf3c1'),
    /**
     * Cube ionicon.
     */
    CUBE('\uf318'),
    /**
     * Disc ionicon.
     */
    DISC('\uf12d'),
    /**
     * Document ionicon.
     */
    DOCUMENT('\uf12f'),
    /**
     * Document text ionicon.
     */
    DOCUMENT_TEXT('\uf12e'),
    /**
     * Drag ionicon.
     */
    DRAG('\uf130'),
    /**
     * Earth ionicon.
     */
    EARTH('\uf276'),
    /**
     * Easel ionicon.
     */
    EASEL('\uf3c2'),
    /**
     * Edit ionicon.
     */
    EDIT('\uf2bf'),
    /**
     * Egg ionicon.
     */
    EGG('\uf277'),
    /**
     * Eject ionicon.
     */
    EJECT('\uf131'),
    /**
     * Email ionicon.
     */
    EMAIL('\uf132'),
    /**
     * Email unread ionicon.
     */
    EMAIL_UNREAD('\uf3c3'),
    /**
     * Erlenmeyer flask ionicon.
     */
    ERLENMEYER_FLASK('\uf3c5'),
    /**
     * Erlenmeyer flask bubbles ionicon.
     */
    ERLENMEYER_FLASK_BUBBLES('\uf3c4'),
    /**
     * Eye ionicon.
     */
    EYE('\uf133'),
    /**
     * Eye disabled ionicon.
     */
    EYE_DISABLED('\uf306'),
    /**
     * Female ionicon.
     */
    FEMALE('\uf278'),
    /**
     * Filing ionicon.
     */
    FILING('\uf134'),
    /**
     * Film marker ionicon.
     */
    FILM_MARKER('\uf135'),
    /**
     * Fireball ionicon.
     */
    FIREBALL('\uf319'),
    /**
     * Flag ionicon.
     */
    FLAG('\uf279'),
    /**
     * Flame ionicon.
     */
    FLAME('\uf31a'),
    /**
     * Flash ionicon.
     */
    FLASH('\uf137'),
    /**
     * Flash off ionicon.
     */
    FLASH_OFF('\uf136'),
    /**
     * Folder ionicon.
     */
    FOLDER('\uf139'),
    /**
     * Fork ionicon.
     */
    FORK('\uf27a'),
    /**
     * Fork repo ionicon.
     */
    FORK_REPO('\uf2c0'),
    /**
     * Forward ionicon.
     */
    FORWARD('\uf13a'),
    /**
     * Funnel ionicon.
     */
    FUNNEL('\uf31b'),
    /**
     * Gear a ionicon.
     */
    GEAR_A('\uf13d'),
    /**
     * Gear b ionicon.
     */
    GEAR_B('\uf13e'),
    /**
     * Grid ionicon.
     */
    GRID('\uf13f'),
    /**
     * Hammer ionicon.
     */
    HAMMER('\uf27b'),
    /**
     * Happy ionicon.
     */
    HAPPY('\uf31c'),
    /**
     * Happy outline ionicon.
     */
    HAPPY_OUTLINE('\uf3c6'),
    /**
     * Headphone ionicon.
     */
    HEADPHONE('\uf140'),
    /**
     * Heart ionicon.
     */
    HEART('\uf141'),
    /**
     * Heart broken ionicon.
     */
    HEART_BROKEN('\uf31d'),
    /**
     * Help ionicon.
     */
    HELP('\uf143'),
    /**
     * Help buoy ionicon.
     */
    HELP_BUOY('\uf27c'),
    /**
     * Help circled ionicon.
     */
    HELP_CIRCLED('\uf142'),
    /**
     * Home ionicon.
     */
    HOME('\uf144'),
    /**
     * Icecream ionicon.
     */
    ICECREAM('\uf27d'),
    /**
     * Image ionicon.
     */
    IMAGE('\uf147'),
    /**
     * Images ionicon.
     */
    IMAGES('\uf148'),
    /**
     * Information ionicon.
     */
    INFORMATION('\uf14a'),
    /**
     * Information circled ionicon.
     */
    INFORMATION_CIRCLED('\uf149'),
    /**
     * Ionic ionicon.
     */
    IONIC('\uf14b'),
    /**
     * Ios alarm ionicon.
     */
    IOS_ALARM('\uf3c8'),
    /**
     * Ios alarm outline ionicon.
     */
    IOS_ALARM_OUTLINE('\uf3c7'),
    /**
     * Ios albums ionicon.
     */
    IOS_ALBUMS('\uf3ca'),
    /**
     * Ios albums outline ionicon.
     */
    IOS_ALBUMS_OUTLINE('\uf3c9'),
    /**
     * Ios americanfootball ionicon.
     */
    IOS_AMERICANFOOTBALL('\uf3cc'),
    /**
     * Ios americanfootball outline ionicon.
     */
    IOS_AMERICANFOOTBALL_OUTLINE('\uf3cb'),
    /**
     * Ios analytics ionicon.
     */
    IOS_ANALYTICS('\uf3ce'),
    /**
     * Ios analytics outline ionicon.
     */
    IOS_ANALYTICS_OUTLINE('\uf3cd'),
    /**
     * Ios arrow back ionicon.
     */
    IOS_ARROW_BACK('\uf3cf'),
    /**
     * Ios arrow down ionicon.
     */
    IOS_ARROW_DOWN('\uf3d0'),
    /**
     * Ios arrow forward ionicon.
     */
    IOS_ARROW_FORWARD('\uf3d1'),
    /**
     * Ios arrow left ionicon.
     */
    IOS_ARROW_LEFT('\uf3d2'),
    /**
     * Ios arrow right ionicon.
     */
    IOS_ARROW_RIGHT('\uf3d3'),
    /**
     * Ios arrow thin down ionicon.
     */
    IOS_ARROW_THIN_DOWN('\uf3d4'),
    /**
     * Ios arrow thin left ionicon.
     */
    IOS_ARROW_THIN_LEFT('\uf3d5'),
    /**
     * Ios arrow thin right ionicon.
     */
    IOS_ARROW_THIN_RIGHT('\uf3d6'),
    /**
     * Ios arrow thin up ionicon.
     */
    IOS_ARROW_THIN_UP('\uf3d7'),
    /**
     * Ios arrow up ionicon.
     */
    IOS_ARROW_UP('\uf3d8'),
    /**
     * Ios at ionicon.
     */
    IOS_AT('\uf3da'),
    /**
     * Ios at outline ionicon.
     */
    IOS_AT_OUTLINE('\uf3d9'),
    /**
     * Ios barcode ionicon.
     */
    IOS_BARCODE('\uf3dc'),
    /**
     * Ios barcode outline ionicon.
     */
    IOS_BARCODE_OUTLINE('\uf3db'),
    /**
     * Ios baseball ionicon.
     */
    IOS_BASEBALL('\uf3de'),
    /**
     * Ios baseball outline ionicon.
     */
    IOS_BASEBALL_OUTLINE('\uf3dd'),
    /**
     * Ios basketball ionicon.
     */
    IOS_BASKETBALL('\uf3e0'),
    /**
     * Ios basketball outline ionicon.
     */
    IOS_BASKETBALL_OUTLINE('\uf3df'),
    /**
     * Ios bell ionicon.
     */
    IOS_BELL('\uf3e2'),
    /**
     * Ios bell outline ionicon.
     */
    IOS_BELL_OUTLINE('\uf3e1'),
    /**
     * Ios body ionicon.
     */
    IOS_BODY('\uf3e4'),
    /**
     * Ios body outline ionicon.
     */
    IOS_BODY_OUTLINE('\uf3e3'),
    /**
     * Ios bolt ionicon.
     */
    IOS_BOLT('\uf3e6'),
    /**
     * Ios bolt outline ionicon.
     */
    IOS_BOLT_OUTLINE('\uf3e5'),
    /**
     * Ios book ionicon.
     */
    IOS_BOOK('\uf3e8'),
    /**
     * Ios book outline ionicon.
     */
    IOS_BOOK_OUTLINE('\uf3e7'),
    /**
     * Ios bookmarks ionicon.
     */
    IOS_BOOKMARKS('\uf3ea'),
    /**
     * Ios bookmarks outline ionicon.
     */
    IOS_BOOKMARKS_OUTLINE('\uf3e9'),
    /**
     * Ios box ionicon.
     */
    IOS_BOX('\uf3ec'),
    /**
     * Ios box outline ionicon.
     */
    IOS_BOX_OUTLINE('\uf3eb'),
    /**
     * Ios briefcase ionicon.
     */
    IOS_BRIEFCASE('\uf3ee'),
    /**
     * Ios briefcase outline ionicon.
     */
    IOS_BRIEFCASE_OUTLINE('\uf3ed'),
    /**
     * Ios browsers ionicon.
     */
    IOS_BROWSERS('\uf3f0'),
    /**
     * Ios browsers outline ionicon.
     */
    IOS_BROWSERS_OUTLINE('\uf3ef'),
    /**
     * Ios calculator ionicon.
     */
    IOS_CALCULATOR('\uf3f2'),
    /**
     * Ios calculator outline ionicon.
     */
    IOS_CALCULATOR_OUTLINE('\uf3f1'),
    /**
     * Ios calendar ionicon.
     */
    IOS_CALENDAR('\uf3f4'),
    /**
     * Ios calendar outline ionicon.
     */
    IOS_CALENDAR_OUTLINE('\uf3f3'),
    /**
     * Ios camera ionicon.
     */
    IOS_CAMERA('\uf3f6'),
    /**
     * Ios camera outline ionicon.
     */
    IOS_CAMERA_OUTLINE('\uf3f5'),
    /**
     * Ios cart ionicon.
     */
    IOS_CART('\uf3f8'),
    /**
     * Ios cart outline ionicon.
     */
    IOS_CART_OUTLINE('\uf3f7'),
    /**
     * Ios chatboxes ionicon.
     */
    IOS_CHATBOXES('\uf3fa'),
    /**
     * Ios chatboxes outline ionicon.
     */
    IOS_CHATBOXES_OUTLINE('\uf3f9'),
    /**
     * Ios chatbubble ionicon.
     */
    IOS_CHATBUBBLE('\uf3fc'),
    /**
     * Ios chatbubble outline ionicon.
     */
    IOS_CHATBUBBLE_OUTLINE('\uf3fb'),
    /**
     * Ios checkmark ionicon.
     */
    IOS_CHECKMARK('\uf3ff'),
    /**
     * Ios checkmark empty ionicon.
     */
    IOS_CHECKMARK_EMPTY('\uf3fd'),
    /**
     * Ios checkmark outline ionicon.
     */
    IOS_CHECKMARK_OUTLINE('\uf3fe'),
    /**
     * Ios circle filled ionicon.
     */
    IOS_CIRCLE_FILLED('\uf400'),
    /**
     * Ios circle outline ionicon.
     */
    IOS_CIRCLE_OUTLINE('\uf401'),
    /**
     * Ios clock ionicon.
     */
    IOS_CLOCK('\uf403'),
    /**
     * Ios clock outline ionicon.
     */
    IOS_CLOCK_OUTLINE('\uf402'),
    /**
     * Ios close ionicon.
     */
    IOS_CLOSE('\uf406'),
    /**
     * Ios close empty ionicon.
     */
    IOS_CLOSE_EMPTY('\uf404'),
    /**
     * Ios close outline ionicon.
     */
    IOS_CLOSE_OUTLINE('\uf405'),
    /**
     * Ios cloud ionicon.
     */
    IOS_CLOUD('\uf40c'),
    /**
     * Ios cloud download ionicon.
     */
    IOS_CLOUD_DOWNLOAD('\uf408'),
    /**
     * Ios cloud download outline ionicon.
     */
    IOS_CLOUD_DOWNLOAD_OUTLINE('\uf407'),
    /**
     * Ios cloud outline ionicon.
     */
    IOS_CLOUD_OUTLINE('\uf409'),
    /**
     * Ios cloud upload ionicon.
     */
    IOS_CLOUD_UPLOAD('\uf40b'),
    /**
     * Ios cloud upload outline ionicon.
     */
    IOS_CLOUD_UPLOAD_OUTLINE('\uf40a'),
    /**
     * Ios cloudy ionicon.
     */
    IOS_CLOUDY('\uf410'),
    /**
     * Ios cloudy night ionicon.
     */
    IOS_CLOUDY_NIGHT('\uf40e'),
    /**
     * Ios cloudy night outline ionicon.
     */
    IOS_CLOUDY_NIGHT_OUTLINE('\uf40d'),
    /**
     * Ios cloudy outline ionicon.
     */
    IOS_CLOUDY_OUTLINE('\uf40f'),
    /**
     * Ios cog ionicon.
     */
    IOS_COG('\uf412'),
    /**
     * Ios cog outline ionicon.
     */
    IOS_COG_OUTLINE('\uf411'),
    /**
     * Ios color filter ionicon.
     */
    IOS_COLOR_FILTER('\uf414'),
    /**
     * Ios color filter outline ionicon.
     */
    IOS_COLOR_FILTER_OUTLINE('\uf413'),
    /**
     * Ios color wand ionicon.
     */
    IOS_COLOR_WAND('\uf416'),
    /**
     * Ios color wand outline ionicon.
     */
    IOS_COLOR_WAND_OUTLINE('\uf415'),
    /**
     * Ios compose ionicon.
     */
    IOS_COMPOSE('\uf418'),
    /**
     * Ios compose outline ionicon.
     */
    IOS_COMPOSE_OUTLINE('\uf417'),
    /**
     * Ios contact ionicon.
     */
    IOS_CONTACT('\uf41a'),
    /**
     * Ios contact outline ionicon.
     */
    IOS_CONTACT_OUTLINE('\uf419'),
    /**
     * Ios copy ionicon.
     */
    IOS_COPY('\uf41c'),
    /**
     * Ios copy outline ionicon.
     */
    IOS_COPY_OUTLINE('\uf41b'),
    /**
     * Ios crop ionicon.
     */
    IOS_CROP('\uf41e'),
    /**
     * Ios crop strong ionicon.
     */
    IOS_CROP_STRONG('\uf41d'),
    /**
     * Ios download ionicon.
     */
    IOS_DOWNLOAD('\uf420'),
    /**
     * Ios download outline ionicon.
     */
    IOS_DOWNLOAD_OUTLINE('\uf41f'),
    /**
     * Ios drag ionicon.
     */
    IOS_DRAG('\uf421'),
    /**
     * Ios email ionicon.
     */
    IOS_EMAIL('\uf423'),
    /**
     * Ios email outline ionicon.
     */
    IOS_EMAIL_OUTLINE('\uf422'),
    /**
     * Ios eye ionicon.
     */
    IOS_EYE('\uf425'),
    /**
     * Ios eye outline ionicon.
     */
    IOS_EYE_OUTLINE('\uf424'),
    /**
     * Ios fastforward ionicon.
     */
    IOS_FASTFORWARD('\uf427'),
    /**
     * Ios fastforward outline ionicon.
     */
    IOS_FASTFORWARD_OUTLINE('\uf426'),
    /**
     * Ios filing ionicon.
     */
    IOS_FILING('\uf429'),
    /**
     * Ios filing outline ionicon.
     */
    IOS_FILING_OUTLINE('\uf428'),
    /**
     * Ios film ionicon.
     */
    IOS_FILM('\uf42b'),
    /**
     * Ios film outline ionicon.
     */
    IOS_FILM_OUTLINE('\uf42a'),
    /**
     * Ios flag ionicon.
     */
    IOS_FLAG('\uf42d'),
    /**
     * Ios flag outline ionicon.
     */
    IOS_FLAG_OUTLINE('\uf42c'),
    /**
     * Ios flame ionicon.
     */
    IOS_FLAME('\uf42f'),
    /**
     * Ios flame outline ionicon.
     */
    IOS_FLAME_OUTLINE('\uf42e'),
    /**
     * Ios flask ionicon.
     */
    IOS_FLASK('\uf431'),
    /**
     * Ios flask outline ionicon.
     */
    IOS_FLASK_OUTLINE('\uf430'),
    /**
     * Ios flower ionicon.
     */
    IOS_FLOWER('\uf433'),
    /**
     * Ios flower outline ionicon.
     */
    IOS_FLOWER_OUTLINE('\uf432'),
    /**
     * Ios folder ionicon.
     */
    IOS_FOLDER('\uf435'),
    /**
     * Ios folder outline ionicon.
     */
    IOS_FOLDER_OUTLINE('\uf434'),
    /**
     * Ios football ionicon.
     */
    IOS_FOOTBALL('\uf437'),
    /**
     * Ios football outline ionicon.
     */
    IOS_FOOTBALL_OUTLINE('\uf436'),
    /**
     * Ios game controller a ionicon.
     */
    IOS_GAME_CONTROLLER_A('\uf439'),
    /**
     * Ios game controller a outline ionicon.
     */
    IOS_GAME_CONTROLLER_A_OUTLINE('\uf438'),
    /**
     * Ios game controller b ionicon.
     */
    IOS_GAME_CONTROLLER_B('\uf43b'),
    /**
     * Ios game controller b outline ionicon.
     */
    IOS_GAME_CONTROLLER_B_OUTLINE('\uf43a'),
    /**
     * Ios gear ionicon.
     */
    IOS_GEAR('\uf43d'),
    /**
     * Ios gear outline ionicon.
     */
    IOS_GEAR_OUTLINE('\uf43c'),
    /**
     * Ios glasses ionicon.
     */
    IOS_GLASSES('\uf43f'),
    /**
     * Ios glasses outline ionicon.
     */
    IOS_GLASSES_OUTLINE('\uf43e'),
    /**
     * Ios grid view ionicon.
     */
    IOS_GRID_VIEW('\uf441'),
    /**
     * Ios grid view outline ionicon.
     */
    IOS_GRID_VIEW_OUTLINE('\uf440'),
    /**
     * Ios heart ionicon.
     */
    IOS_HEART('\uf443'),
    /**
     * Ios heart outline ionicon.
     */
    IOS_HEART_OUTLINE('\uf442'),
    /**
     * Ios help ionicon.
     */
    IOS_HELP('\uf446'),
    /**
     * Ios help empty ionicon.
     */
    IOS_HELP_EMPTY('\uf444'),
    /**
     * Ios help outline ionicon.
     */
    IOS_HELP_OUTLINE('\uf445'),
    /**
     * Ios home ionicon.
     */
    IOS_HOME('\uf448'),
    /**
     * Ios home outline ionicon.
     */
    IOS_HOME_OUTLINE('\uf447'),
    /**
     * Ios infinite ionicon.
     */
    IOS_INFINITE('\uf44a'),
    /**
     * Ios infinite outline ionicon.
     */
    IOS_INFINITE_OUTLINE('\uf449'),
    /**
     * Ios information ionicon.
     */
    IOS_INFORMATION('\uf44d'),
    /**
     * Ios information empty ionicon.
     */
    IOS_INFORMATION_EMPTY('\uf44b'),
    /**
     * Ios information outline ionicon.
     */
    IOS_INFORMATION_OUTLINE('\uf44c'),
    /**
     * Ios ionic outline ionicon.
     */
    IOS_IONIC_OUTLINE('\uf44e'),
    /**
     * Ios keypad ionicon.
     */
    IOS_KEYPAD('\uf450'),
    /**
     * Ios keypad outline ionicon.
     */
    IOS_KEYPAD_OUTLINE('\uf44f'),
    /**
     * Ios lightbulb ionicon.
     */
    IOS_LIGHTBULB('\uf452'),
    /**
     * Ios lightbulb outline ionicon.
     */
    IOS_LIGHTBULB_OUTLINE('\uf451'),
    /**
     * Ios list ionicon.
     */
    IOS_LIST('\uf454'),
    /**
     * Ios list outline ionicon.
     */
    IOS_LIST_OUTLINE('\uf453'),
    /**
     * Ios location ionicon.
     */
    IOS_LOCATION('\uf456'),
    /**
     * Ios location outline ionicon.
     */
    IOS_LOCATION_OUTLINE('\uf455'),
    /**
     * Ios locked ionicon.
     */
    IOS_LOCKED('\uf458'),
    /**
     * Ios locked outline ionicon.
     */
    IOS_LOCKED_OUTLINE('\uf457'),
    /**
     * Ios loop ionicon.
     */
    IOS_LOOP('\uf45a'),
    /**
     * Ios loop strong ionicon.
     */
    IOS_LOOP_STRONG('\uf459'),
    /**
     * Ios medical ionicon.
     */
    IOS_MEDICAL('\uf45c'),
    /**
     * Ios medical outline ionicon.
     */
    IOS_MEDICAL_OUTLINE('\uf45b'),
    /**
     * Ios medkit ionicon.
     */
    IOS_MEDKIT('\uf45e'),
    /**
     * Ios medkit outline ionicon.
     */
    IOS_MEDKIT_OUTLINE('\uf45d'),
    /**
     * Ios mic ionicon.
     */
    IOS_MIC('\uf461'),
    /**
     * Ios mic off ionicon.
     */
    IOS_MIC_OFF('\uf45f'),
    /**
     * Ios mic outline ionicon.
     */
    IOS_MIC_OUTLINE('\uf460'),
    /**
     * Ios minus ionicon.
     */
    IOS_MINUS('\uf464'),
    /**
     * Ios minus empty ionicon.
     */
    IOS_MINUS_EMPTY('\uf462'),
    /**
     * Ios minus outline ionicon.
     */
    IOS_MINUS_OUTLINE('\uf463'),
    /**
     * Ios monitor ionicon.
     */
    IOS_MONITOR('\uf466'),
    /**
     * Ios monitor outline ionicon.
     */
    IOS_MONITOR_OUTLINE('\uf465'),
    /**
     * Ios moon ionicon.
     */
    IOS_MOON('\uf468'),
    /**
     * Ios moon outline ionicon.
     */
    IOS_MOON_OUTLINE('\uf467'),
    /**
     * Ios more ionicon.
     */
    IOS_MORE('\uf46a'),
    /**
     * Ios more outline ionicon.
     */
    IOS_MORE_OUTLINE('\uf469'),
    /**
     * Ios musical note ionicon.
     */
    IOS_MUSICAL_NOTE('\uf46b'),
    /**
     * Ios musical notes ionicon.
     */
    IOS_MUSICAL_NOTES('\uf46c'),
    /**
     * Ios navigate ionicon.
     */
    IOS_NAVIGATE('\uf46e'),
    /**
     * Ios navigate outline ionicon.
     */
    IOS_NAVIGATE_OUTLINE('\uf46d'),
    /**
     * Ios nutrition ionicon.
     */
    IOS_NUTRITION('\uf470'),
    /**
     * Ios nutrition outline ionicon.
     */
    IOS_NUTRITION_OUTLINE('\uf46f'),
    /**
     * Ios paper ionicon.
     */
    IOS_PAPER('\uf472'),
    /**
     * Ios paper outline ionicon.
     */
    IOS_PAPER_OUTLINE('\uf471'),
    /**
     * Ios paperplane ionicon.
     */
    IOS_PAPERPLANE('\uf474'),
    /**
     * Ios paperplane outline ionicon.
     */
    IOS_PAPERPLANE_OUTLINE('\uf473'),
    /**
     * Ios partlysunny ionicon.
     */
    IOS_PARTLYSUNNY('\uf476'),
    /**
     * Ios partlysunny outline ionicon.
     */
    IOS_PARTLYSUNNY_OUTLINE('\uf475'),
    /**
     * Ios pause ionicon.
     */
    IOS_PAUSE('\uf478'),
    /**
     * Ios pause outline ionicon.
     */
    IOS_PAUSE_OUTLINE('\uf477'),
    /**
     * Ios paw ionicon.
     */
    IOS_PAW('\uf47a'),
    /**
     * Ios paw outline ionicon.
     */
    IOS_PAW_OUTLINE('\uf479'),
    /**
     * Ios people ionicon.
     */
    IOS_PEOPLE('\uf47c'),
    /**
     * Ios people outline ionicon.
     */
    IOS_PEOPLE_OUTLINE('\uf47b'),
    /**
     * Ios person ionicon.
     */
    IOS_PERSON('\uf47e'),
    /**
     * Ios person outline ionicon.
     */
    IOS_PERSON_OUTLINE('\uf47d'),
    /**
     * Ios personadd ionicon.
     */
    IOS_PERSONADD('\uf480'),
    /**
     * Ios personadd outline ionicon.
     */
    IOS_PERSONADD_OUTLINE('\uf47f'),
    /**
     * Ios photos ionicon.
     */
    IOS_PHOTOS('\uf482'),
    /**
     * Ios photos outline ionicon.
     */
    IOS_PHOTOS_OUTLINE('\uf481'),
    /**
     * Ios pie ionicon.
     */
    IOS_PIE('\uf484'),
    /**
     * Ios pie outline ionicon.
     */
    IOS_PIE_OUTLINE('\uf483'),
    /**
     * Ios pint ionicon.
     */
    IOS_PINT('\uf486'),
    /**
     * Ios pint outline ionicon.
     */
    IOS_PINT_OUTLINE('\uf485'),
    /**
     * Ios play ionicon.
     */
    IOS_PLAY('\uf488'),
    /**
     * Ios play outline ionicon.
     */
    IOS_PLAY_OUTLINE('\uf487'),
    /**
     * Ios plus ionicon.
     */
    IOS_PLUS('\uf48b'),
    /**
     * Ios plus empty ionicon.
     */
    IOS_PLUS_EMPTY('\uf489'),
    /**
     * Ios plus outline ionicon.
     */
    IOS_PLUS_OUTLINE('\uf48a'),
    /**
     * Ios pricetag ionicon.
     */
    IOS_PRICETAG('\uf48d'),
    /**
     * Ios pricetag outline ionicon.
     */
    IOS_PRICETAG_OUTLINE('\uf48c'),
    /**
     * Ios pricetags ionicon.
     */
    IOS_PRICETAGS('\uf48f'),
    /**
     * Ios pricetags outline ionicon.
     */
    IOS_PRICETAGS_OUTLINE('\uf48e'),
    /**
     * Ios printer ionicon.
     */
    IOS_PRINTER('\uf491'),
    /**
     * Ios printer outline ionicon.
     */
    IOS_PRINTER_OUTLINE('\uf490'),
    /**
     * Ios pulse ionicon.
     */
    IOS_PULSE('\uf493'),
    /**
     * Ios pulse strong ionicon.
     */
    IOS_PULSE_STRONG('\uf492'),
    /**
     * Ios rainy ionicon.
     */
    IOS_RAINY('\uf495'),
    /**
     * Ios rainy outline ionicon.
     */
    IOS_RAINY_OUTLINE('\uf494'),
    /**
     * Ios recording ionicon.
     */
    IOS_RECORDING('\uf497'),
    /**
     * Ios recording outline ionicon.
     */
    IOS_RECORDING_OUTLINE('\uf496'),
    /**
     * Ios redo ionicon.
     */
    IOS_REDO('\uf499'),
    /**
     * Ios redo outline ionicon.
     */
    IOS_REDO_OUTLINE('\uf498'),
    /**
     * Ios refresh ionicon.
     */
    IOS_REFRESH('\uf49c'),
    /**
     * Ios refresh empty ionicon.
     */
    IOS_REFRESH_EMPTY('\uf49a'),
    /**
     * Ios refresh outline ionicon.
     */
    IOS_REFRESH_OUTLINE('\uf49b'),
    /**
     * Ios reload ionicon.
     */
    IOS_RELOAD('\uf49d'),
    /**
     * Ios reverse camera ionicon.
     */
    IOS_REVERSE_CAMERA('\uf49f'),
    /**
     * Ios reverse camera outline ionicon.
     */
    IOS_REVERSE_CAMERA_OUTLINE('\uf49e'),
    /**
     * Ios rewind ionicon.
     */
    IOS_REWIND('\uf4a1'),
    /**
     * Ios rewind outline ionicon.
     */
    IOS_REWIND_OUTLINE('\uf4a0'),
    /**
     * Ios rose ionicon.
     */
    IOS_ROSE('\uf4a3'),
    /**
     * Ios rose outline ionicon.
     */
    IOS_ROSE_OUTLINE('\uf4a2'),
    /**
     * Ios search ionicon.
     */
    IOS_SEARCH('\uf4a5'),
    /**
     * Ios search strong ionicon.
     */
    IOS_SEARCH_STRONG('\uf4a4'),
    /**
     * Ios settings ionicon.
     */
    IOS_SETTINGS('\uf4a7'),
    /**
     * Ios settings strong ionicon.
     */
    IOS_SETTINGS_STRONG('\uf4a6'),
    /**
     * Ios shuffle ionicon.
     */
    IOS_SHUFFLE('\uf4a9'),
    /**
     * Ios shuffle strong ionicon.
     */
    IOS_SHUFFLE_STRONG('\uf4a8'),
    /**
     * Ios skipbackward ionicon.
     */
    IOS_SKIPBACKWARD('\uf4ab'),
    /**
     * Ios skipbackward outline ionicon.
     */
    IOS_SKIPBACKWARD_OUTLINE('\uf4aa'),
    /**
     * Ios skipforward ionicon.
     */
    IOS_SKIPFORWARD('\uf4ad'),
    /**
     * Ios skipforward outline ionicon.
     */
    IOS_SKIPFORWARD_OUTLINE('\uf4ac'),
    /**
     * Ios snowy ionicon.
     */
    IOS_SNOWY('\uf4ae'),
    /**
     * Ios speedometer ionicon.
     */
    IOS_SPEEDOMETER('\uf4b0'),
    /**
     * Ios speedometer outline ionicon.
     */
    IOS_SPEEDOMETER_OUTLINE('\uf4af'),
    /**
     * Ios star ionicon.
     */
    IOS_STAR('\uf4b3'),
    /**
     * Ios star half ionicon.
     */
    IOS_STAR_HALF('\uf4b1'),
    /**
     * Ios star outline ionicon.
     */
    IOS_STAR_OUTLINE('\uf4b2'),
    /**
     * Ios stopwatch ionicon.
     */
    IOS_STOPWATCH('\uf4b5'),
    /**
     * Ios stopwatch outline ionicon.
     */
    IOS_STOPWATCH_OUTLINE('\uf4b4'),
    /**
     * Ios sunny ionicon.
     */
    IOS_SUNNY('\uf4b7'),
    /**
     * Ios sunny outline ionicon.
     */
    IOS_SUNNY_OUTLINE('\uf4b6'),
    /**
     * Ios telephone ionicon.
     */
    IOS_TELEPHONE('\uf4b9'),
    /**
     * Ios telephone outline ionicon.
     */
    IOS_TELEPHONE_OUTLINE('\uf4b8'),
    /**
     * Ios tennisball ionicon.
     */
    IOS_TENNISBALL('\uf4bb'),
    /**
     * Ios tennisball outline ionicon.
     */
    IOS_TENNISBALL_OUTLINE('\uf4ba'),
    /**
     * Ios thunderstorm ionicon.
     */
    IOS_THUNDERSTORM('\uf4bd'),
    /**
     * Ios thunderstorm outline ionicon.
     */
    IOS_THUNDERSTORM_OUTLINE('\uf4bc'),
    /**
     * Ios time ionicon.
     */
    IOS_TIME('\uf4bf'),
    /**
     * Ios time outline ionicon.
     */
    IOS_TIME_OUTLINE('\uf4be'),
    /**
     * Ios timer ionicon.
     */
    IOS_TIMER('\uf4c1'),
    /**
     * Ios timer outline ionicon.
     */
    IOS_TIMER_OUTLINE('\uf4c0'),
    /**
     * Ios toggle ionicon.
     */
    IOS_TOGGLE('\uf4c3'),
    /**
     * Ios toggle outline ionicon.
     */
    IOS_TOGGLE_OUTLINE('\uf4c2'),
    /**
     * Ios trash ionicon.
     */
    IOS_TRASH('\uf4c5'),
    /**
     * Ios trash outline ionicon.
     */
    IOS_TRASH_OUTLINE('\uf4c4'),
    /**
     * Ios undo ionicon.
     */
    IOS_UNDO('\uf4c7'),
    /**
     * Ios undo outline ionicon.
     */
    IOS_UNDO_OUTLINE('\uf4c6'),
    /**
     * Ios unlocked ionicon.
     */
    IOS_UNLOCKED('\uf4c9'),
    /**
     * Ios unlocked outline ionicon.
     */
    IOS_UNLOCKED_OUTLINE('\uf4c8'),
    /**
     * Ios upload ionicon.
     */
    IOS_UPLOAD('\uf4cb'),
    /**
     * Ios upload outline ionicon.
     */
    IOS_UPLOAD_OUTLINE('\uf4ca'),
    /**
     * Ios videocam ionicon.
     */
    IOS_VIDEOCAM('\uf4cd'),
    /**
     * Ios videocam outline ionicon.
     */
    IOS_VIDEOCAM_OUTLINE('\uf4cc'),
    /**
     * Ios volume high ionicon.
     */
    IOS_VOLUME_HIGH('\uf4ce'),
    /**
     * Ios volume low ionicon.
     */
    IOS_VOLUME_LOW('\uf4cf'),
    /**
     * Ios wineglass ionicon.
     */
    IOS_WINEGLASS('\uf4d1'),
    /**
     * Ios wineglass outline ionicon.
     */
    IOS_WINEGLASS_OUTLINE('\uf4d0'),
    /**
     * Ios world ionicon.
     */
    IOS_WORLD('\uf4d3'),
    /**
     * Ios world outline ionicon.
     */
    IOS_WORLD_OUTLINE('\uf4d2'),
    /**
     * Ipad ionicon.
     */
    IPAD('\uf1f9'),
    /**
     * Iphone ionicon.
     */
    IPHONE('\uf1fa'),
    /**
     * Ipod ionicon.
     */
    IPOD('\uf1fb'),
    /**
     * Jet ionicon.
     */
    JET('\uf295'),
    /**
     * Key ionicon.
     */
    KEY('\uf296'),
    /**
     * Knife ionicon.
     */
    KNIFE('\uf297'),
    /**
     * Laptop ionicon.
     */
    LAPTOP('\uf1fc'),
    /**
     * Leaf ionicon.
     */
    LEAF('\uf1fd'),
    /**
     * Levels ionicon.
     */
    LEVELS('\uf298'),
    /**
     * Lightbulb ionicon.
     */
    LIGHTBULB('\uf299'),
    /**
     * Link ionicon.
     */
    LINK('\uf1fe'),
    /**
     * Load a ionicon.
     */
    LOAD_A('\uf29a'),
    /**
     * Load b ionicon.
     */
    LOAD_B('\uf29b'),
    /**
     * Load c ionicon.
     */
    LOAD_C('\uf29c'),
    /**
     * Load d ionicon.
     */
    LOAD_D('\uf29d'),
    /**
     * Location ionicon.
     */
    LOCATION('\uf1ff'),
    /**
     * Lock combination ionicon.
     */
    LOCK_COMBINATION('\uf4d4'),
    /**
     * Locked ionicon.
     */
    LOCKED('\uf200'),
    /**
     * Log in ionicon.
     */
    LOG_IN('\uf29e'),
    /**
     * Log out ionicon.
     */
    LOG_OUT('\uf29f'),
    /**
     * Loop ionicon.
     */
    LOOP('\uf201'),
    /**
     * Magnet ionicon.
     */
    MAGNET('\uf2a0'),
    /**
     * Male ionicon.
     */
    MALE('\uf2a1'),
    /**
     * Man ionicon.
     */
    MAN('\uf202'),
    /**
     * Map ionicon.
     */
    MAP('\uf203'),
    /**
     * Medkit ionicon.
     */
    MEDKIT('\uf2a2'),
    /**
     * Merge ionicon.
     */
    MERGE('\uf33f'),
    /**
     * Mic a ionicon.
     */
    MIC_A('\uf204'),
    /**
     * Mic b ionicon.
     */
    MIC_B('\uf205'),
    /**
     * Mic c ionicon.
     */
    MIC_C('\uf206'),
    /**
     * Minus ionicon.
     */
    MINUS('\uf209'),
    /**
     * Minus circled ionicon.
     */
    MINUS_CIRCLED('\uf207'),
    /**
     * Minus round ionicon.
     */
    MINUS_ROUND('\uf208'),
    /**
     * Model s ionicon.
     */
    MODEL_S('\uf2c1'),
    /**
     * Monitor ionicon.
     */
    MONITOR('\uf20a'),
    /**
     * More ionicon.
     */
    MORE('\uf20b'),
    /**
     * Mouse ionicon.
     */
    MOUSE('\uf340'),
    /**
     * Music note ionicon.
     */
    MUSIC_NOTE('\uf20c'),
    /**
     * Navicon ionicon.
     */
    NAVICON('\uf20e'),
    /**
     * Navicon round ionicon.
     */
    NAVICON_ROUND('\uf20d'),
    /**
     * Navigate ionicon.
     */
    NAVIGATE('\uf2a3'),
    /**
     * Network ionicon.
     */
    NETWORK('\uf341'),
    /**
     * No smoking ionicon.
     */
    NO_SMOKING('\uf2c2'),
    /**
     * Nuclear ionicon.
     */
    NUCLEAR('\uf2a4'),
    /**
     * Outlet ionicon.
     */
    OUTLET('\uf342'),
    /**
     * Paintbrush ionicon.
     */
    PAINTBRUSH('\uf4d5'),
    /**
     * Paintbucket ionicon.
     */
    PAINTBUCKET('\uf4d6'),
    /**
     * Paper airplane ionicon.
     */
    PAPER_AIRPLANE('\uf2c3'),
    /**
     * Paperclip ionicon.
     */
    PAPERCLIP('\uf20f'),
    /**
     * Pause ionicon.
     */
    PAUSE('\uf210'),
    /**
     * Person ionicon.
     */
    PERSON('\uf213'),
    /**
     * Person add ionicon.
     */
    PERSON_ADD('\uf211'),
    /**
     * Person stalker ionicon.
     */
    PERSON_STALKER('\uf212'),
    /**
     * Pie graph ionicon.
     */
    PIE_GRAPH('\uf2a5'),
    /**
     * Pin ionicon.
     */
    PIN('\uf2a6'),
    /**
     * Pinpoint ionicon.
     */
    PINPOINT('\uf2a7'),
    /**
     * Pizza ionicon.
     */
    PIZZA('\uf2a8'),
    /**
     * Plane ionicon.
     */
    PLANE('\uf214'),
    /**
     * Planet ionicon.
     */
    PLANET('\uf343'),
    /**
     * Play ionicon.
     */
    PLAY('\uf215'),
    /**
     * Playstation ionicon.
     */
    PLAYSTATION('\uf30a'),
    /**
     * Plus ionicon.
     */
    PLUS('\uf218'),
    /**
     * Plus circled ionicon.
     */
    PLUS_CIRCLED('\uf216'),
    /**
     * Plus round ionicon.
     */
    PLUS_ROUND('\uf217'),
    /**
     * Podium ionicon.
     */
    PODIUM('\uf344'),
    /**
     * Pound ionicon.
     */
    POUND('\uf219'),
    /**
     * Power ionicon.
     */
    POWER('\uf2a9'),
    /**
     * Pricetag ionicon.
     */
    PRICETAG('\uf2aa'),
    /**
     * Pricetags ionicon.
     */
    PRICETAGS('\uf2ab'),
    /**
     * Printer ionicon.
     */
    PRINTER('\uf21a'),
    /**
     * Pull request ionicon.
     */
    PULL_REQUEST('\uf345'),
    /**
     * Qr scanner ionicon.
     */
    QR_SCANNER('\uf346'),
    /**
     * Quote ionicon.
     */
    QUOTE('\uf347'),
    /**
     * Radio waves ionicon.
     */
    RADIO_WAVES('\uf2ac'),
    /**
     * Record ionicon.
     */
    RECORD('\uf21b'),
    /**
     * Refresh ionicon.
     */
    REFRESH('\uf21c'),
    /**
     * Reply ionicon.
     */
    REPLY('\uf21e'),
    /**
     * Reply all ionicon.
     */
    REPLY_ALL('\uf21d'),
    /**
     * Ribbon a ionicon.
     */
    RIBBON_A('\uf348'),
    /**
     * Ribbon b ionicon.
     */
    RIBBON_B('\uf349'),
    /**
     * Sad ionicon.
     */
    SAD('\uf34a'),
    /**
     * Sad outline ionicon.
     */
    SAD_OUTLINE('\uf4d7'),
    /**
     * Scissors ionicon.
     */
    SCISSORS('\uf34b'),
    /**
     * Search ionicon.
     */
    SEARCH('\uf21f'),
    /**
     * Settings ionicon.
     */
    SETTINGS('\uf2ad'),
    /**
     * Share ionicon.
     */
    SHARE('\uf220'),
    /**
     * Shuffle ionicon.
     */
    SHUFFLE('\uf221'),
    /**
     * Skip backward ionicon.
     */
    SKIP_BACKWARD('\uf222'),
    /**
     * Skip forward ionicon.
     */
    SKIP_FORWARD('\uf223'),
    /**
     * Social android ionicon.
     */
    SOCIAL_ANDROID('\uf225'),
    /**
     * Social android outline ionicon.
     */
    SOCIAL_ANDROID_OUTLINE('\uf224'),
    /**
     * Social angular ionicon.
     */
    SOCIAL_ANGULAR('\uf4d9'),
    /**
     * Social angular outline ionicon.
     */
    SOCIAL_ANGULAR_OUTLINE('\uf4d8'),
    /**
     * Social apple ionicon.
     */
    SOCIAL_APPLE('\uf227'),
    /**
     * Social apple outline ionicon.
     */
    SOCIAL_APPLE_OUTLINE('\uf226'),
    /**
     * Social bitcoin ionicon.
     */
    SOCIAL_BITCOIN('\uf2af'),
    /**
     * Social bitcoin outline ionicon.
     */
    SOCIAL_BITCOIN_OUTLINE('\uf2ae'),
    /**
     * Social buffer ionicon.
     */
    SOCIAL_BUFFER('\uf229'),
    /**
     * Social buffer outline ionicon.
     */
    SOCIAL_BUFFER_OUTLINE('\uf228'),
    /**
     * Social chrome ionicon.
     */
    SOCIAL_CHROME('\uf4db'),
    /**
     * Social chrome outline ionicon.
     */
    SOCIAL_CHROME_OUTLINE('\uf4da'),
    /**
     * Social codepen ionicon.
     */
    SOCIAL_CODEPEN('\uf4dd'),
    /**
     * Social codepen outline ionicon.
     */
    SOCIAL_CODEPEN_OUTLINE('\uf4dc'),
    /**
     * Social css 3 ionicon.
     */
    SOCIAL_CSS3('\uf4df'),
    /**
     * Social css 3 outline ionicon.
     */
    SOCIAL_CSS3_OUTLINE('\uf4de'),
    /**
     * Social designernews ionicon.
     */
    SOCIAL_DESIGNERNEWS('\uf22b'),
    /**
     * Social designernews outline ionicon.
     */
    SOCIAL_DESIGNERNEWS_OUTLINE('\uf22a'),
    /**
     * Social dribbble ionicon.
     */
    SOCIAL_DRIBBBLE('\uf22d'),
    /**
     * Social dribbble outline ionicon.
     */
    SOCIAL_DRIBBBLE_OUTLINE('\uf22c'),
    /**
     * Social dropbox ionicon.
     */
    SOCIAL_DROPBOX('\uf22f'),
    /**
     * Social dropbox outline ionicon.
     */
    SOCIAL_DROPBOX_OUTLINE('\uf22e'),
    /**
     * Social euro ionicon.
     */
    SOCIAL_EURO('\uf4e1'),
    /**
     * Social euro outline ionicon.
     */
    SOCIAL_EURO_OUTLINE('\uf4e0'),
    /**
     * Social facebook ionicon.
     */
    SOCIAL_FACEBOOK('\uf231'),
    /**
     * Social facebook outline ionicon.
     */
    SOCIAL_FACEBOOK_OUTLINE('\uf230'),
    /**
     * Social foursquare ionicon.
     */
    SOCIAL_FOURSQUARE('\uf34d'),
    /**
     * Social foursquare outline ionicon.
     */
    SOCIAL_FOURSQUARE_OUTLINE('\uf34c'),
    /**
     * Social freebsd devil ionicon.
     */
    SOCIAL_FREEBSD_DEVIL('\uf2c4'),
    /**
     * Social github ionicon.
     */
    SOCIAL_GITHUB('\uf233'),
    /**
     * Social github outline ionicon.
     */
    SOCIAL_GITHUB_OUTLINE('\uf232'),
    /**
     * Social google ionicon.
     */
    SOCIAL_GOOGLE('\uf34f'),
    /**
     * Social google outline ionicon.
     */
    SOCIAL_GOOGLE_OUTLINE('\uf34e'),
    /**
     * Social googleplus ionicon.
     */
    SOCIAL_GOOGLEPLUS('\uf235'),
    /**
     * Social googleplus outline ionicon.
     */
    SOCIAL_GOOGLEPLUS_OUTLINE('\uf234'),
    /**
     * Social hackernews ionicon.
     */
    SOCIAL_HACKERNEWS('\uf237'),
    /**
     * Social hackernews outline ionicon.
     */
    SOCIAL_HACKERNEWS_OUTLINE('\uf236'),
    /**
     * Social html 5 ionicon.
     */
    SOCIAL_HTML5('\uf4e3'),
    /**
     * Social html 5 outline ionicon.
     */
    SOCIAL_HTML5_OUTLINE('\uf4e2'),
    /**
     * Social instagram ionicon.
     */
    SOCIAL_INSTAGRAM('\uf351'),
    /**
     * Social instagram outline ionicon.
     */
    SOCIAL_INSTAGRAM_OUTLINE('\uf350'),
    /**
     * Social javascript ionicon.
     */
    SOCIAL_JAVASCRIPT('\uf4e5'),
    /**
     * Social javascript outline ionicon.
     */
    SOCIAL_JAVASCRIPT_OUTLINE('\uf4e4'),
    /**
     * Social linkedin ionicon.
     */
    SOCIAL_LINKEDIN('\uf239'),
    /**
     * Social linkedin outline ionicon.
     */
    SOCIAL_LINKEDIN_OUTLINE('\uf238'),
    /**
     * Social markdown ionicon.
     */
    SOCIAL_MARKDOWN('\uf4e6'),
    /**
     * Social nodejs ionicon.
     */
    SOCIAL_NODEJS('\uf4e7'),
    /**
     * Social octocat ionicon.
     */
    SOCIAL_OCTOCAT('\uf4e8'),
    /**
     * Social pinterest ionicon.
     */
    SOCIAL_PINTEREST('\uf2b1'),
    /**
     * Social pinterest outline ionicon.
     */
    SOCIAL_PINTEREST_OUTLINE('\uf2b0'),
    /**
     * Social python ionicon.
     */
    SOCIAL_PYTHON('\uf4e9'),
    /**
     * Social reddit ionicon.
     */
    SOCIAL_REDDIT('\uf23b'),
    /**
     * Social reddit outline ionicon.
     */
    SOCIAL_REDDIT_OUTLINE('\uf23a'),
    /**
     * Social rss ionicon.
     */
    SOCIAL_RSS('\uf23d'),
    /**
     * Social rss outline ionicon.
     */
    SOCIAL_RSS_OUTLINE('\uf23c'),
    /**
     * Social sass ionicon.
     */
    SOCIAL_SASS('\uf4ea'),
    /**
     * Social skype ionicon.
     */
    SOCIAL_SKYPE('\uf23f'),
    /**
     * Social skype outline ionicon.
     */
    SOCIAL_SKYPE_OUTLINE('\uf23e'),
    /**
     * Social snapchat ionicon.
     */
    SOCIAL_SNAPCHAT('\uf4ec'),
    /**
     * Social snapchat outline ionicon.
     */
    SOCIAL_SNAPCHAT_OUTLINE('\uf4eb'),
    /**
     * Social tumblr ionicon.
     */
    SOCIAL_TUMBLR('\uf241'),
    /**
     * Social tumblr outline ionicon.
     */
    SOCIAL_TUMBLR_OUTLINE('\uf240'),
    /**
     * Social tux ionicon.
     */
    SOCIAL_TUX('\uf2c5'),
    /**
     * Social twitch ionicon.
     */
    SOCIAL_TWITCH('\uf4ee'),
    /**
     * Social twitch outline ionicon.
     */
    SOCIAL_TWITCH_OUTLINE('\uf4ed'),
    /**
     * Social twitter ionicon.
     */
    SOCIAL_TWITTER('\uf243'),
    /**
     * Social twitter outline ionicon.
     */
    SOCIAL_TWITTER_OUTLINE('\uf242'),
    /**
     * Social usd ionicon.
     */
    SOCIAL_USD('\uf353'),
    /**
     * Social usd outline ionicon.
     */
    SOCIAL_USD_OUTLINE('\uf352'),
    /**
     * Social vimeo ionicon.
     */
    SOCIAL_VIMEO('\uf245'),
    /**
     * Social vimeo outline ionicon.
     */
    SOCIAL_VIMEO_OUTLINE('\uf244'),
    /**
     * Social whatsapp ionicon.
     */
    SOCIAL_WHATSAPP('\uf4f0'),
    /**
     * Social whatsapp outline ionicon.
     */
    SOCIAL_WHATSAPP_OUTLINE('\uf4ef'),
    /**
     * Social windows ionicon.
     */
    SOCIAL_WINDOWS('\uf247'),
    /**
     * Social windows outline ionicon.
     */
    SOCIAL_WINDOWS_OUTLINE('\uf246'),
    /**
     * Social wordpress ionicon.
     */
    SOCIAL_WORDPRESS('\uf249'),
    /**
     * Social wordpress outline ionicon.
     */
    SOCIAL_WORDPRESS_OUTLINE('\uf248'),
    /**
     * Social yahoo ionicon.
     */
    SOCIAL_YAHOO('\uf24b'),
    /**
     * Social yahoo outline ionicon.
     */
    SOCIAL_YAHOO_OUTLINE('\uf24a'),
    /**
     * Social yen ionicon.
     */
    SOCIAL_YEN('\uf4f2'),
    /**
     * Social yen outline ionicon.
     */
    SOCIAL_YEN_OUTLINE('\uf4f1'),
    /**
     * Social youtube ionicon.
     */
    SOCIAL_YOUTUBE('\uf24d'),
    /**
     * Social youtube outline ionicon.
     */
    SOCIAL_YOUTUBE_OUTLINE('\uf24c'),
    /**
     * Soup can ionicon.
     */
    SOUP_CAN('\uf4f4'),
    /**
     * Soup can outline ionicon.
     */
    SOUP_CAN_OUTLINE('\uf4f3'),
    /**
     * Speakerphone ionicon.
     */
    SPEAKERPHONE('\uf2b2'),
    /**
     * Speedometer ionicon.
     */
    SPEEDOMETER('\uf2b3'),
    /**
     * Spoon ionicon.
     */
    SPOON('\uf2b4'),
    /**
     * Star ionicon.
     */
    STAR('\uf24e'),
    /**
     * Stats bars ionicon.
     */
    STATS_BARS('\uf2b5'),
    /**
     * Steam ionicon.
     */
    STEAM('\uf30b'),
    /**
     * Stop ionicon.
     */
    STOP('\uf24f'),
    /**
     * Thermometer ionicon.
     */
    THERMOMETER('\uf2b6'),
    /**
     * Thumbsdown ionicon.
     */
    THUMBSDOWN('\uf250'),
    /**
     * Thumbsup ionicon.
     */
    THUMBSUP('\uf251'),
    /**
     * Toggle ionicon.
     */
    TOGGLE('\uf355'),
    /**
     * Toggle filled ionicon.
     */
    TOGGLE_FILLED('\uf354'),
    /**
     * Transgender ionicon.
     */
    TRANSGENDER('\uf4f5'),
    /**
     * Trash a ionicon.
     */
    TRASH_A('\uf252'),
    /**
     * Trash b ionicon.
     */
    TRASH_B('\uf253'),
    /**
     * Trophy ionicon.
     */
    TROPHY('\uf356'),
    /**
     * Tshirt ionicon.
     */
    TSHIRT('\uf4f7'),
    /**
     * Tshirt outline ionicon.
     */
    TSHIRT_OUTLINE('\uf4f6'),
    /**
     * Umbrella ionicon.
     */
    UMBRELLA('\uf2b7'),
    /**
     * University ionicon.
     */
    UNIVERSITY('\uf357'),
    /**
     * Unlocked ionicon.
     */
    UNLOCKED('\uf254'),
    /**
     * Upload ionicon.
     */
    UPLOAD('\uf255'),
    /**
     * Usb ionicon.
     */
    USB('\uf2b8'),
    /**
     * Videocamera ionicon.
     */
    VIDEOCAMERA('\uf256'),
    /**
     * Volume high ionicon.
     */
    VOLUME_HIGH('\uf257'),
    /**
     * Volume low ionicon.
     */
    VOLUME_LOW('\uf258'),
    /**
     * Volume medium ionicon.
     */
    VOLUME_MEDIUM('\uf259'),
    /**
     * Volume mute ionicon.
     */
    VOLUME_MUTE('\uf25a'),
    /**
     * Wand ionicon.
     */
    WAND('\uf358'),
    /**
     * Waterdrop ionicon.
     */
    WATERDROP('\uf25b'),
    /**
     * Wifi ionicon.
     */
    WIFI('\uf25c'),
    /**
     * Wineglass ionicon.
     */
    WINEGLASS('\uf2b9'),
    /**
     * Woman ionicon.
     */
    WOMAN('\uf25d'),
    /**
     * Wrench ionicon.
     */
    WRENCH('\uf2ba'),
    /**
     * Xbox ionicon.
     */
    XBOX('\uf30c');

	private static final String IONICONS_SET = "/com/rapidminer/resources/fonts/ionicons.ttf";

	private static String FONT;

	private static final String ERROR = "<span style=\"color:red; font-weight: bold;\">?</span>";

	static {
		try (InputStream stream = Ionicon.class.getResourceAsStream(IONICONS_SET)) {
			Font ionicons = Font.createFont(Font.TRUETYPE_FONT, stream);
			GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
			genv.registerFont(ionicons);

			FONT = "<span style=\"font-family: " + ionicons.getFamily() + "; font-style: normal;\">%s</span>";

		} catch (FontFormatException | IOException e) {
			LogService.getRoot().log(Level.WARNING, "com.rapidminer.gui.tools.Ionicon.font_load_failed", e.getMessage());
		}
	}

	private char character;

	private Ionicon(char character) {
		this.character = character;
	}

    /**
     * Gets html.
     *
     * @return the html for including the ionicon into a html label
     */
    public String getHtml() {
		if (FONT != null) {
			return String.format(FONT, character);
		} else {
			return ERROR;
		}

	}
}

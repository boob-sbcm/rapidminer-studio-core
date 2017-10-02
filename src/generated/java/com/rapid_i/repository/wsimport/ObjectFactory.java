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
package com.rapid_i.repository.wsimport;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.rapid_i.repository.wsimport package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetProcessContents_QNAME = new QName("http://service.web.rapidanalytics.de/", "getProcessContents");
    private final static QName _GetAccessRightsResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "getAccessRightsResponse");
    private final static QName _GetEntry_QNAME = new QName("http://service.web.rapidanalytics.de/", "getEntry");
    private final static QName _DeleteEntry_QNAME = new QName("http://service.web.rapidanalytics.de/", "deleteEntry");
    private final static QName _StoreProcess_QNAME = new QName("http://service.web.rapidanalytics.de/", "storeProcess");
    private final static QName _StartNewRevisionResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "startNewRevisionResponse");
    private final static QName _RenameResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "renameResponse");
    private final static QName _GetAccessRights_QNAME = new QName("http://service.web.rapidanalytics.de/", "getAccessRights");
    private final static QName _MakeFolderResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "makeFolderResponse");
    private final static QName _MakeFolder_QNAME = new QName("http://service.web.rapidanalytics.de/", "makeFolder");
    private final static QName _Move_QNAME = new QName("http://service.web.rapidanalytics.de/", "move");
    private final static QName _GetFolderContentsResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "getFolderContentsResponse");
    private final static QName _DeleteEntryResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "deleteEntryResponse");
    private final static QName _Rename_QNAME = new QName("http://service.web.rapidanalytics.de/", "rename");
    private final static QName _SetAccessRightsResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "setAccessRightsResponse");
    private final static QName _GetProcessContentsResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "getProcessContentsResponse");
    private final static QName _GetFolderContents_QNAME = new QName("http://service.web.rapidanalytics.de/", "getFolderContents");
    private final static QName _GetEntryResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "getEntryResponse");
    private final static QName _StoreProcessResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "storeProcessResponse");
    private final static QName _GetAllGroupNames_QNAME = new QName("http://service.web.rapidanalytics.de/", "getAllGroupNames");
    private final static QName _CreateBlobResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "createBlobResponse");
    private final static QName _MoveResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "moveResponse");
    private final static QName _CreateBlob_QNAME = new QName("http://service.web.rapidanalytics.de/", "createBlob");
    private final static QName _StartNewRevision_QNAME = new QName("http://service.web.rapidanalytics.de/", "startNewRevision");
    private final static QName _SetAccessRights_QNAME = new QName("http://service.web.rapidanalytics.de/", "setAccessRights");
    private final static QName _GetAllGroupNamesResponse_QNAME = new QName("http://service.web.rapidanalytics.de/", "getAllGroupNamesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rapid_i.repository.wsimport
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetFolderContentsResponse }
     *
     * @return the get folder contents response
     */
    public GetFolderContentsResponse createGetFolderContentsResponse() {
        return new GetFolderContentsResponse();
    }

    /**
     * Create an instance of {@link DeleteEntryResponse }
     *
     * @return the delete entry response
     */
    public DeleteEntryResponse createDeleteEntryResponse() {
        return new DeleteEntryResponse();
    }

    /**
     * Create an instance of {@link Rename }
     *
     * @return the rename
     */
    public Rename createRename() {
        return new Rename();
    }

    /**
     * Create an instance of {@link GetFolderContents }
     *
     * @return the get folder contents
     */
    public GetFolderContents createGetFolderContents() {
        return new GetFolderContents();
    }

    /**
     * Create an instance of {@link GetProcessContentsResponse }
     *
     * @return the get process contents response
     */
    public GetProcessContentsResponse createGetProcessContentsResponse() {
        return new GetProcessContentsResponse();
    }

    /**
     * Create an instance of {@link SetAccessRightsResponse }
     *
     * @return the set access rights response
     */
    public SetAccessRightsResponse createSetAccessRightsResponse() {
        return new SetAccessRightsResponse();
    }

    /**
     * Create an instance of {@link StoreProcessResponse }
     *
     * @return the store process response
     */
    public StoreProcessResponse createStoreProcessResponse() {
        return new StoreProcessResponse();
    }

    /**
     * Create an instance of {@link GetEntryResponse }
     *
     * @return the get entry response
     */
    public GetEntryResponse createGetEntryResponse() {
        return new GetEntryResponse();
    }

    /**
     * Create an instance of {@link GetEntry }
     *
     * @return the get entry
     */
    public GetEntry createGetEntry() {
        return new GetEntry();
    }

    /**
     * Create an instance of {@link GetAccessRightsResponse }
     *
     * @return the get access rights response
     */
    public GetAccessRightsResponse createGetAccessRightsResponse() {
        return new GetAccessRightsResponse();
    }

    /**
     * Create an instance of {@link GetProcessContents }
     *
     * @return the get process contents
     */
    public GetProcessContents createGetProcessContents() {
        return new GetProcessContents();
    }

    /**
     * Create an instance of {@link DeleteEntry }
     *
     * @return the delete entry
     */
    public DeleteEntry createDeleteEntry() {
        return new DeleteEntry();
    }

    /**
     * Create an instance of {@link StoreProcess }
     *
     * @return the store process
     */
    public StoreProcess createStoreProcess() {
        return new StoreProcess();
    }

    /**
     * Create an instance of {@link StartNewRevisionResponse }
     *
     * @return the start new revision response
     */
    public StartNewRevisionResponse createStartNewRevisionResponse() {
        return new StartNewRevisionResponse();
    }

    /**
     * Create an instance of {@link RenameResponse }
     *
     * @return the rename response
     */
    public RenameResponse createRenameResponse() {
        return new RenameResponse();
    }

    /**
     * Create an instance of {@link GetAccessRights }
     *
     * @return the get access rights
     */
    public GetAccessRights createGetAccessRights() {
        return new GetAccessRights();
    }

    /**
     * Create an instance of {@link MakeFolderResponse }
     *
     * @return the make folder response
     */
    public MakeFolderResponse createMakeFolderResponse() {
        return new MakeFolderResponse();
    }

    /**
     * Create an instance of {@link MakeFolder }
     *
     * @return the make folder
     */
    public MakeFolder createMakeFolder() {
        return new MakeFolder();
    }

    /**
     * Create an instance of {@link Move }
     *
     * @return the move
     */
    public Move createMove() {
        return new Move();
    }

    /**
     * Create an instance of {@link StartNewRevision }
     *
     * @return the start new revision
     */
    public StartNewRevision createStartNewRevision() {
        return new StartNewRevision();
    }

    /**
     * Create an instance of {@link SetAccessRights }
     *
     * @return the set access rights
     */
    public SetAccessRights createSetAccessRights() {
        return new SetAccessRights();
    }

    /**
     * Create an instance of {@link GetAllGroupNamesResponse }
     *
     * @return the get all group names response
     */
    public GetAllGroupNamesResponse createGetAllGroupNamesResponse() {
        return new GetAllGroupNamesResponse();
    }

    /**
     * Create an instance of {@link GetAllGroupNames }
     *
     * @return the get all group names
     */
    public GetAllGroupNames createGetAllGroupNames() {
        return new GetAllGroupNames();
    }

    /**
     * Create an instance of {@link CreateBlobResponse }
     *
     * @return the create blob response
     */
    public CreateBlobResponse createCreateBlobResponse() {
        return new CreateBlobResponse();
    }

    /**
     * Create an instance of {@link MoveResponse }
     *
     * @return the move response
     */
    public MoveResponse createMoveResponse() {
        return new MoveResponse();
    }

    /**
     * Create an instance of {@link CreateBlob }
     *
     * @return the create blob
     */
    public CreateBlob createCreateBlob() {
        return new CreateBlob();
    }

    /**
     * Create an instance of {@link EntryResponse }
     *
     * @return the entry response
     */
    public EntryResponse createEntryResponse() {
        return new EntryResponse();
    }

    /**
     * Create an instance of {@link Response }
     *
     * @return the response
     */
    public Response createResponse() {
        return new Response();
    }

    /**
     * Create an instance of {@link ProcessContentsResponse }
     *
     * @return the process contents response
     */
    public ProcessContentsResponse createProcessContentsResponse() {
        return new ProcessContentsResponse();
    }

    /**
     * Create an instance of {@link AccessRights }
     *
     * @return the access rights
     */
    public AccessRights createAccessRights() {
        return new AccessRights();
    }

    /**
     * Create an instance of {@link FolderContentsResponse }
     *
     * @return the folder contents response
     */
    public FolderContentsResponse createFolderContentsResponse() {
        return new FolderContentsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcessContents }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getProcessContents")
    public JAXBElement<GetProcessContents> createGetProcessContents(GetProcessContents value) {
        return new JAXBElement<GetProcessContents>(_GetProcessContents_QNAME, GetProcessContents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccessRightsResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getAccessRightsResponse")
    public JAXBElement<GetAccessRightsResponse> createGetAccessRightsResponse(GetAccessRightsResponse value) {
        return new JAXBElement<GetAccessRightsResponse>(_GetAccessRightsResponse_QNAME, GetAccessRightsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEntry }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getEntry")
    public JAXBElement<GetEntry> createGetEntry(GetEntry value) {
        return new JAXBElement<GetEntry>(_GetEntry_QNAME, GetEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEntry }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "deleteEntry")
    public JAXBElement<DeleteEntry> createDeleteEntry(DeleteEntry value) {
        return new JAXBElement<DeleteEntry>(_DeleteEntry_QNAME, DeleteEntry.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreProcess }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "storeProcess")
    public JAXBElement<StoreProcess> createStoreProcess(StoreProcess value) {
        return new JAXBElement<StoreProcess>(_StoreProcess_QNAME, StoreProcess.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartNewRevisionResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "startNewRevisionResponse")
    public JAXBElement<StartNewRevisionResponse> createStartNewRevisionResponse(StartNewRevisionResponse value) {
        return new JAXBElement<StartNewRevisionResponse>(_StartNewRevisionResponse_QNAME, StartNewRevisionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "renameResponse")
    public JAXBElement<RenameResponse> createRenameResponse(RenameResponse value) {
        return new JAXBElement<RenameResponse>(_RenameResponse_QNAME, RenameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccessRights }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getAccessRights")
    public JAXBElement<GetAccessRights> createGetAccessRights(GetAccessRights value) {
        return new JAXBElement<GetAccessRights>(_GetAccessRights_QNAME, GetAccessRights.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeFolderResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "makeFolderResponse")
    public JAXBElement<MakeFolderResponse> createMakeFolderResponse(MakeFolderResponse value) {
        return new JAXBElement<MakeFolderResponse>(_MakeFolderResponse_QNAME, MakeFolderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MakeFolder }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "makeFolder")
    public JAXBElement<MakeFolder> createMakeFolder(MakeFolder value) {
        return new JAXBElement<MakeFolder>(_MakeFolder_QNAME, MakeFolder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Move }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "move")
    public JAXBElement<Move> createMove(Move value) {
        return new JAXBElement<Move>(_Move_QNAME, Move.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFolderContentsResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getFolderContentsResponse")
    public JAXBElement<GetFolderContentsResponse> createGetFolderContentsResponse(GetFolderContentsResponse value) {
        return new JAXBElement<GetFolderContentsResponse>(_GetFolderContentsResponse_QNAME, GetFolderContentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteEntryResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "deleteEntryResponse")
    public JAXBElement<DeleteEntryResponse> createDeleteEntryResponse(DeleteEntryResponse value) {
        return new JAXBElement<DeleteEntryResponse>(_DeleteEntryResponse_QNAME, DeleteEntryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Rename }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "rename")
    public JAXBElement<Rename> createRename(Rename value) {
        return new JAXBElement<Rename>(_Rename_QNAME, Rename.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetAccessRightsResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "setAccessRightsResponse")
    public JAXBElement<SetAccessRightsResponse> createSetAccessRightsResponse(SetAccessRightsResponse value) {
        return new JAXBElement<SetAccessRightsResponse>(_SetAccessRightsResponse_QNAME, SetAccessRightsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcessContentsResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getProcessContentsResponse")
    public JAXBElement<GetProcessContentsResponse> createGetProcessContentsResponse(GetProcessContentsResponse value) {
        return new JAXBElement<GetProcessContentsResponse>(_GetProcessContentsResponse_QNAME, GetProcessContentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFolderContents }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getFolderContents")
    public JAXBElement<GetFolderContents> createGetFolderContents(GetFolderContents value) {
        return new JAXBElement<GetFolderContents>(_GetFolderContents_QNAME, GetFolderContents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEntryResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getEntryResponse")
    public JAXBElement<GetEntryResponse> createGetEntryResponse(GetEntryResponse value) {
        return new JAXBElement<GetEntryResponse>(_GetEntryResponse_QNAME, GetEntryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StoreProcessResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "storeProcessResponse")
    public JAXBElement<StoreProcessResponse> createStoreProcessResponse(StoreProcessResponse value) {
        return new JAXBElement<StoreProcessResponse>(_StoreProcessResponse_QNAME, StoreProcessResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllGroupNames }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getAllGroupNames")
    public JAXBElement<GetAllGroupNames> createGetAllGroupNames(GetAllGroupNames value) {
        return new JAXBElement<GetAllGroupNames>(_GetAllGroupNames_QNAME, GetAllGroupNames.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBlobResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "createBlobResponse")
    public JAXBElement<CreateBlobResponse> createCreateBlobResponse(CreateBlobResponse value) {
        return new JAXBElement<CreateBlobResponse>(_CreateBlobResponse_QNAME, CreateBlobResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "moveResponse")
    public JAXBElement<MoveResponse> createMoveResponse(MoveResponse value) {
        return new JAXBElement<MoveResponse>(_MoveResponse_QNAME, MoveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateBlob }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "createBlob")
    public JAXBElement<CreateBlob> createCreateBlob(CreateBlob value) {
        return new JAXBElement<CreateBlob>(_CreateBlob_QNAME, CreateBlob.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartNewRevision }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "startNewRevision")
    public JAXBElement<StartNewRevision> createStartNewRevision(StartNewRevision value) {
        return new JAXBElement<StartNewRevision>(_StartNewRevision_QNAME, StartNewRevision.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetAccessRights }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "setAccessRights")
    public JAXBElement<SetAccessRights> createSetAccessRights(SetAccessRights value) {
        return new JAXBElement<SetAccessRights>(_SetAccessRights_QNAME, SetAccessRights.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllGroupNamesResponse }{@code >}}
     *
     * @param value the value
     * @return the jaxb element
     */
    @XmlElementDecl(namespace = "http://service.web.rapidanalytics.de/", name = "getAllGroupNamesResponse")
    public JAXBElement<GetAllGroupNamesResponse> createGetAllGroupNamesResponse(GetAllGroupNamesResponse value) {
        return new JAXBElement<GetAllGroupNamesResponse>(_GetAllGroupNamesResponse_QNAME, GetAllGroupNamesResponse.class, null, value);
    }

}

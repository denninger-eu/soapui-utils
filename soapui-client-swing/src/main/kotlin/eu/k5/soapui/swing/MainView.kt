package eu.k5.soapui.swing

import com.eviware.soapui.model.project.Project
import eu.k5.soapui.swing.karate.KarateExporterView
import java.awt.BorderLayout
import java.awt.Dimension
import java.io.File
import java.util.prefs.Preferences
import javax.swing.*
import javax.swing.event.ListSelectionEvent


class MainView(
    private val model: MainModel
) {
    private val controller = MainController(model, this)

    private val mainFrame: JFrame = JFrame("SoapUI Tools")
    private val mainLayout: BorderLayout = BorderLayout(2, 2)


    private val projectPanel = JPanel()
    private val projectsScrollPane: JScrollPane

    private val projects: JList<MainModel.ProjectModel>

    private val projectStructure = JPanel()

    init {

        projectPanel.layout = BoxLayout(projectPanel, BoxLayout.Y_AXIS)
        val label = JLabel("Projects")
        projectPanel.add(label)

        val addProject = JButton("Add")
        addProject.addActionListener { controller.doAddProject() }
        projectPanel.add(addProject)


        projects = JList(model.projects)
        projects.addListSelectionListener { updateSelectedProject(it) }

        projectsScrollPane = JScrollPane(projects)
        projectsScrollPane.preferredSize = Dimension(250, 80)

        projectPanel.add(projectsScrollPane)
        projectStructure.layout = BoxLayout(projectStructure, BoxLayout.Y_AXIS)
        projectStructure.add(ProjectView(model).asPanel())
        projectPanel.add(projectStructure)


        mainFrame.add(projectPanel, BorderLayout.WEST);
        mainFrame.add(KarateExporterView(model.exporterModel).asPanel(), BorderLayout.CENTER)

        mainFrame.pack()
        mainFrame.setSize(800, 800)
        mainFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        model.currentProject.registerOnEdt { old, new -> updateProjectStructure(old) }

    }

    private fun updateProjectStructure(projectModel: MainModel.ProjectModel?) {
        //    println("update structure")
        /*     projectStructure.remove(0)
             val project = model.currentProject.getEntry()
             if (project == null) {
                 return
             }

             println("setting structure view")

             val projectView = ProjectView(project)
             projectStructure.add(projectView.asPanel())
             mainFrame.repaint()
             println("projects repainted")*/
    }

    private fun updateSelectedProject(event: ListSelectionEvent) {
        if (!event.valueIsAdjusting && projects.selectedIndex >= 0) {
            model.currentProject.update(projects.selectedValue)
        }
    }

    fun display() {
        mainFrame.isVisible = true
    }

    fun openProject(): File? {
        val chooser = JFileChooser()

        val prefs: Preferences = Preferences.userNodeForPackage(MainView::class.java)

        chooser.fileSelectionMode = JFileChooser.FILES_ONLY

        val parentFolder = File(prefs.get("folder", ""))
        if (parentFolder.exists()) {
            chooser.currentDirectory = parentFolder
        }
        val result = chooser.showOpenDialog(null)
        if (result != JFileChooser.APPROVE_OPTION) {
            return null
        }
        val selectedFile = chooser.selectedFile
        prefs.put("folder", selectedFile.parentFile.canonicalPath)
        return selectedFile
    }

    companion object {

    }
}
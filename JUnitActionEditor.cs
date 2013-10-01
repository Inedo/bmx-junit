using System;
using System.Text.RegularExpressions;
using System.Web.UI.WebControls;
using Inedo.BuildMaster.Data;
using Inedo.BuildMaster.Extensibility.Actions;
using Inedo.BuildMaster.Web.Controls;
using Inedo.BuildMaster.Web.Controls.Extensions;
using Inedo.Web.Controls;

namespace Inedo.BuildMasterExtensions.JUnit
{
    internal sealed class JUnitActionEditor : ActionEditorBase
    {
        private ValidatingTextBox txtGroupName;
        private SourceControlFileFolderPicker ctlJavaPath;
        private ValidatingTextBox txtExtensionDirectories;
        private ValidatingTextBox txtSearchPattern;

        public override bool DisplaySourceDirectory { get { return true; } }

        public override void BindToForm(ActionBase extension)
        {
            this.EnsureChildControls();

            var jUnit = (JUnitAction)extension;
            if (jUnit.ExtensionDirectories != null)
                txtExtensionDirectories.Text = string.Join(Environment.NewLine, jUnit.ExtensionDirectories);
            ctlJavaPath.Text = jUnit.JavaPath;
            txtSearchPattern.Text = jUnit.SearchPattern;
            txtGroupName.Text = jUnit.GroupName;
        }

        public override ActionBase CreateFromForm()
        {
            this.EnsureChildControls();

            return new JUnitAction
            {
                ExtensionDirectories = Regex.Split(this.txtExtensionDirectories.Text, "\r?\n"),
                JavaPath = ctlJavaPath.Text,
                SearchPattern = txtSearchPattern.Text,
                GroupName = txtGroupName.Text
            };
        }

        protected override void CreateChildControls()
        {
            this.txtGroupName = new ValidatingTextBox
            {
                Required = true,
                Width = Unit.Pixel(300),
                Text = this.PlanRow[TableDefs.PlanActionGroups_Extended.Deployable_Name].ToString()
            };

            this.ctlJavaPath = new SourceControlFileFolderPicker { ServerId = this.ServerId };

            this.txtExtensionDirectories = new ValidatingTextBox
            {
                TextMode = TextBoxMode.MultiLine,
                Rows = 4,
                Columns = 100,
                Width = Unit.Pixel(300)
            };

            this.txtSearchPattern = new ValidatingTextBox
            {
                Width = Unit.Pixel(300),
                Text = (new JUnitAction()).SearchPattern
            };

            this.Controls.Add(
                new FormFieldGroup(
                    "Test Group Name",
                    "The name associated with this group of tests",
                    false,
                    new StandardFormField("Group:", this.txtGroupName)),
                new FormFieldGroup(
                    "Java Path",
                    "The path to java.exe",
                    false,
                    new StandardFormField("Path:", this.ctlJavaPath)),
                new FormFieldGroup(
                    "Extensions Paths",
                    "The relative path of Java extensions. Note that jUnit 4.x must be in the default or one of these directories.",
                    false,
                    new StandardFormField(string.Empty, this.txtExtensionDirectories)),
                new FormFieldGroup(
                    "Search Pattern",
                    "The file mask that indicates which classes are to be tested.",
                    true,
                    new StandardFormField(string.Empty, this.txtSearchPattern)
                )
            );
        }
    }
}

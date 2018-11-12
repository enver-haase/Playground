import java.awt.desktop.SystemEventListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class OsserRename {

	private final static String root = "/Users/ehaase/Desktop/Huggett";

	private static class RenameAction{
		Path newPath;
		final Path oldPath;

		RenameAction(Path newPath, Path oldPath){
			this.newPath = newPath;
			this.oldPath = oldPath;
		}

		void execute() throws IOException{
			Files.move(oldPath, newPath);
		}
	}

	private static Path calculateSimpleRename(Path path){

		final String[] prefixesToKill = {"showDZA_P_", "P_"};

		String fileName = path.getFileName().toString();
		for (String prefix : prefixesToKill){
			if (fileName.startsWith(prefix)){
				fileName = fileName.substring(prefix.length());
				break;
			}
		}
		String killAllFrom = "_001";
		int idx = fileName.indexOf(killAllFrom);
		if (idx != -1){
			fileName = fileName.substring(0, idx);
		}

		if (isTypeA(path)){
			fileName = fileName.subSequence(0, 3)+"."+fileName.subSequence(3, 4)+"."+fileName.subSequence(4,6)+"."+fileName.subSequence(6, 9)+"."+fileName.subSequence(9, 10);
			fileName += ".Pxx";
			fileName += ".tif";
			return Path.of(path.getParent().toString(), fileName);
		}

		if (isTypeB(path)){

			fileName = fileName.substring(1); // drop the leading '0'.

			StringBuilder partNo = new StringBuilder();
			idx = 4;
			char c = fileName.charAt(idx);
			while (c >= '0' && c <= '9'){
				partNo.append(c);
				if (++idx == fileName.length()){
					break;
				}
				c = fileName.charAt(idx);
			}

			String variant = fileName.substring(4+partNo.length());
			if (variant.length() > 0){
				variant = " " + variant;
			}

			if (partNo.toString().startsWith("0")){
				partNo = new StringBuilder(partNo.substring(1));
			}

			fileName = fileName.subSequence(0, 1) + " " + fileName.subSequence(1, 3) + " " + fileName.subSequence(3, 4) + " " + partNo + variant;
			fileName += ".Pxx";
			fileName += ".tif";
			return Path.of(path.getParent().toString(), fileName);
		}

		return path;

	}

	/**
	 * Type A is a normal, so-called 'numeric' part number (but letters are allowed in certain places).
	 */
	private static boolean isTypeA(Path path){
		String fileName = path.getFileName().toString();
		return Pattern.matches("(showDZA_)?P_\\d\\d\\d\\w\\d\\d\\d\\d\\d\\d_001(.)*.tif", fileName);
	}

	/**
	 * Type B is a normal, so-called 'alphanumeric' part number.
	 */
	private static boolean isTypeB(Path path){
		String fileName = path.getFileName().toString();
		return Pattern.matches("(showDZA_)?P_0[FGM]\\d\\d\\p{Upper}\\d\\d\\d(\\p{Upper})?_001(.)*.tif", fileName);
	}

	/*
	 * Type A: 245.F.64.030.2.Px.tif
	 *         245.F.64.030.2.Px.c.tif
	 */
	private static boolean isFinishedTypeA(Path path){
		String fileName = path.getFileName().toString();
		return Pattern.matches("\\d\\d\\d\\.\\w\\.\\d\\d\\.\\d\\d\\d\\.\\d(\\.[a-z])?\\.P[0-9x][0-9x]\\.tif", fileName);
	}

	private static boolean isFinishedTypeB(Path path){
		String fileName = path.getFileName().toString();
		return Pattern.matches("[FGM] \\d\\d \\w \\d\\d\\d( \\p{Alpha}(\\p{Alpha})?)?\\.P[0-9x][0-9x]\\.tif", fileName);
	}

	private static void printList(String header, List list){
		printHeader(header, list);
		System.out.println();
		list.stream().sorted().forEach(System.out::println);
		System.out.println();
	}

	private static void printHeader(String header, Collection c) {
		header += " ("+c.size()+")";
		System.out.println(header);
		for (int i = 0; i < header.length(); i++) {
			System.out.print("=");
		}
	}

	public static void main(String[] args) throws IOException {

		Stream<Path> stream = Files.walk(Paths.get(root));

		List<Path> allNames = new ArrayList<>();
		stream.filter(Files::isRegularFile).forEach(allNames::add);


		stream = allNames.stream();
		List<Path> typeAs = new ArrayList<>();
		stream.filter(OsserRename::isTypeA).forEach(typeAs::add);
		printList("Type A, to be renamed", typeAs);

		stream = allNames.stream();
		List<Path> typeBs = new ArrayList<>();
		stream.filter(OsserRename::isTypeB).forEach(typeBs::add);
		printList("Type B, to be renamed", typeBs);

		stream = allNames.stream();
		List<Path> finishedTypeAs = new ArrayList<>();
		stream.filter(OsserRename::isFinishedTypeA).forEach(finishedTypeAs::add);
		printList("Type A, already renamed, to be ignored", finishedTypeAs);

		stream = allNames.stream();
		List<Path> finishedTypeBs = new ArrayList<>();
		stream.filter(OsserRename::isFinishedTypeB).forEach(finishedTypeBs::add);
		printList("Type B, already renamed, to be ignored", finishedTypeBs);

		stream = allNames.stream();
		List<Path> unknowns = new ArrayList<>();
		stream.forEach(unknowns::add);
		unknowns.removeAll(typeAs);
		unknowns.removeAll(typeBs);
		unknowns.removeAll(finishedTypeAs);
		unknowns.removeAll(finishedTypeBs);
		printList("UNKNOWN / INCORRECTLY NAMED, to be ignored", unknowns);

		int sum = unknowns.size() + finishedTypeAs.size() + finishedTypeBs.size() + typeAs.size() + typeBs.size();
		System.out.println("\nNumber of files in above sections, summed up: " + sum);
		System.out.println("Number of all regular files: " + allNames.size());
		if (allNames.size() != sum){
			System.err.print("Numbers differ, internal error. Bailing out.");
			System.exit(1);
		}
		else{
			System.out.println("Plausibility check passed, continuing.");
		}

		// create a map of target file names a a key, and let the value be a 'RenameAction' that doubles that target file name but also as the source file name.
		HashMap<Path, Set<RenameAction>> targetPaths = new HashMap<>();
		List<Path> allToRename = new ArrayList<>();
		allToRename.addAll(typeAs);
		allToRename.addAll(typeBs);
		allToRename.forEach(path -> {
			Path newPath = calculateSimpleRename(path);
			if (!newPath.equals(path)){
				RenameAction renameAction = new RenameAction(newPath, path);
				Set<RenameAction> set = targetPaths.get(newPath);
				if (set == null){
					set = new HashSet<>();
				}
				set.add(renameAction);

				targetPaths.put(newPath, set);
			}
		});

		// Resolve the situation where two or more file names shall be renamed into a single new file name.
		// Note that Iterator.remove() is the only safe way to modify a collection during iteration; the behavior is unspecified if the underlying collection is modified in any other way while the iteration is in progress.
		// Source: docs.oracle > The Collection Interface
		HashMap<Path, Set<RenameAction>> newTargetPaths = new HashMap<>();
		for (Iterator<Map.Entry<Path, Set<RenameAction>>> iterator = targetPaths.entrySet().iterator(); iterator.hasNext(); ){
			Map.Entry<Path, Set<RenameAction>> entry = iterator.next();
			if (entry.getValue().size() > 1){
				iterator.remove();

				char addMe = 'a';
				for (RenameAction renameAction : entry.getValue()) {
					// modify the actions
					String fileName = renameAction.newPath.getFileName().toString();
					fileName = fileName.substring(0, fileName.length() - 4); // drop ".tif"
					fileName += ".";
					fileName += addMe;
					fileName += ".tif";
					addMe++; // next file be b, c, d, and so on
					Path finalPath = Path.of(renameAction.newPath.getParent().toString(), fileName);
					renameAction.newPath = finalPath;
					// and keep the new ones
					newTargetPaths.put(finalPath, Set.of(renameAction));
				}
			}
		}
		targetPaths.putAll(newTargetPaths);

		printHeader("Renaming plan", targetPaths.keySet());
		targetPaths.keySet().stream().sorted().forEach( targetPath -> {
			System.out.println(targetPath + " <- " + targetPaths.get(targetPath).iterator().next().oldPath);
		});
	}
}
